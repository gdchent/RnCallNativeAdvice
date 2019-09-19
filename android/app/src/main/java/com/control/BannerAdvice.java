package com.control;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTBannerAd;
import com.rncallnativeadvice.RnActivity;
import com.union_test.toutiao.activity.BannerActivity;
import com.union_test.toutiao.config.TTAdManagerHolder;
import com.union_test.toutiao.utils.TToast;

//第一种广告类型
public class BannerAdvice {

    private static BannerAdvice bannerAdvice;
    private static TTAdNative mTTAdNative;
    private static TTAdDislike mTTAdDislike;
    private static Context context;
    private static FrameLayout mBannerContainer;
    //也就说RN调用的时候 这个是要执行的第一步 初始化这些东西
    private BannerAdvice(Context context) {
        this.context=context ;
        mBannerContainer=new FrameLayout(context);
        mBannerContainer.setBackgroundColor(Color.GREEN);
        //step2:创建TTAdNative对象，createAdNative(Context context) banner广告context需要传入Activity对象
        //mTTAdNative = TTAdManagerHolder.get().createAdNative(context);
        mTTAdNative= RnActivity.mTTAdNative ;//由于这里不需要展示视图 而上下文必须是Activity 所以这里直接放到RnActivity
        //step3:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdManagerHolder.get().requestPermissionIfNecessary(context);
    }

    //暴露出一个单例对象
    public static BannerAdvice getInstance(Context context){
         if(bannerAdvice==null){
             bannerAdvice=new BannerAdvice(context);
         }
         return bannerAdvice ;
    }

    //暴露Framelayout
    public FrameLayout getBannerContainer(){
        return mBannerContainer;
    }

    //在原生把事情做完之后 需要暴露给RN视图界面 这里是最后的逻辑
    public  void showBannerToRn(){

    }
    //显示下载类广告 RN那边触发这种类型广告
    public void showBannerDownload(){
        loadBannerAd("901121895");
    }
    //显示落地叶的广告
    public void showBannerLandingpage(){
        loadBannerAd("901121987");
    }

    //根据广告位id来加载视图 填充到所需要的位置
    private void loadBannerAd(String codeId) {
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId) //广告位id
                .setSupportDeepLink(true)
                .setImageAcceptedSize(600, 257)
                .build();
        //step5:请求广告，对请求回调的广告作渲染处理
        mTTAdNative.loadBannerAd(adSlot, new TTAdNative.BannerAdListener() {

            @Override
            public void onError(int code, String message) {
                TToast.show(context, "load error : " + code + ", " + message);
                mBannerContainer.removeAllViews();

            }

            @Override
            public void onBannerAdLoad(final TTBannerAd ad) {
                if (ad == null) {
                    return;
                }
                View bannerView = ad.getBannerView();
                if (bannerView == null) {
                    return;
                }
                //设置轮播的时间间隔  间隔在30s到120秒之间的值，不设置默认不轮播
                ad.setSlideIntervalTime(30 * 1000);
                mBannerContainer.removeAllViews();
                Log.i("gdchent","加载banner逻辑前");
                //也就是我现在要用RN来替换
                mBannerContainer.addView(bannerView);
                Log.i("gdchent","加载banner逻辑后");
                //设置广告互动监听回调
                ad.setBannerInteractionListener(new TTBannerAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        TToast.show(context, "广告被点击");
                    }

                    @Override
                    public void onAdShow(View view, int type) {
                        TToast.show(context, "广告展示");
                    }
                });
                //（可选）设置下载类广告的下载监听
                bindDownloadListener(ad);
                //在banner中显示网盟提供的dislike icon，有助于广告投放精准度提升
                ad.setShowDislikeIcon(new TTAdDislike.DislikeInteractionCallback() {
                    @Override
                    public void onSelected(int position, String value) {
                        TToast.show(context, "点击 " + value);
                        //用户选择不喜欢原因后，移除广告展示
                        mBannerContainer.removeAllViews();
                    }

                    @Override
                    public void onCancel() {
                        TToast.show(context, "点击取消 ");
                    }
                });

                //获取网盟dislike dialog，您可以在您应用中本身自定义的dislike icon 按钮中设置 mTTAdDislike.showDislikeDialog();
                /*mTTAdDislike = ad.getDislikeDialog(new TTAdDislike.DislikeInteractionCallback() {
                        @Override
                        public void onSelected(int position, String value) {
                            TToast.show(context, "点击 " + value);
                        }
                        @Override
                        public void onCancel() {
                            TToast.show(context, "点击取消 ");
                        }
                    });
                if (mTTAdDislike != null) {
                    XXX.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mTTAdDislike.showDislikeDialog();
                        }
                    });
                } */

            }
        });
    }

    private boolean mHasShowDownloadActive = false;
    private void bindDownloadListener(TTBannerAd ad) {

        ad.setDownloadListener(new TTAppDownloadListener() {
            @Override
            public void onIdle() {
                TToast.show(context, "点击图片开始下载", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                if (!mHasShowDownloadActive) {
                    mHasShowDownloadActive = true;
                    TToast.show(context, "下载中，点击图片暂停", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                TToast.show(context, "下载暂停，点击图片继续", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                TToast.show(context, "下载失败，点击图片重新下载", Toast.LENGTH_LONG);
            }

            @Override
            public void onInstalled(String fileName, String appName) {
                TToast.show(context, "安装完成，点击图片打开", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                TToast.show(context, "点击图片安装", Toast.LENGTH_LONG);
            }
        });
    }
}
