package com.union_test.toutiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.TextView;

import com.bytedance.sdk.openadsdk.downloadnew.core.ExitInstallListener;

import com.rncallnativeadvice.R;
import com.union_test.toutiao.config.TTAdManagerHolder;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvVersion = findViewById(R.id.tv_version);
        String ver = getString(R.string.main_sdk_version_tip, TTAdManagerHolder.get().getSDKVersion());
        tvVersion.setText(ver);

        bindButton(R.id.btn_main_banner, BannerActivity.class);
        bindButton(R.id.btn_main_feed_lv, FeedListActivity.class);
        bindButton(R.id.btn_main_feed_rv, FeedRecyclerActivity.class);
        bindButton(R.id.btn_mian_insert, InteractionActivity.class);
        bindButton(R.id.btn_mian_splash, SplashActivity.class);
        bindButton(R.id.btn_mian_reward, RewardVideoActivity.class);
        bindButton(R.id.btn_main_full, FullScreenVideoActivity.class);
        bindButton(R.id.btn_main_banner_native, NativeBannerActivity.class);
        bindButton(R.id.btn_main_banner_express, BannerExpressActivity.class);
        bindButton(R.id.btn_main_interstitial_native, NativeInteractionActivity.class);
        bindButton(R.id.btn_main_interstitial_express, InteractionExpressActivity.class);
        bindButton(R.id.btn_main_draw_native, DrawNativeVideoActivity.class);
        bindButton(R.id.btn_native_vertical_video, NativeVerticalVideoActivity.class);
        bindButton(R.id.btn_native_express, NativeExpressActivity.class);
        bindButton(R.id.btn_native_express_list, NativeExpressListActivity.class);
        bindButton(R.id.btn_locker, com.locker.activity.LockerActivity.class);
        // 申请部分权限,建议在sdk初始化前申请,如：READ_PHONE_STATE、ACCESS_COARSE_LOCATION及ACCESS_FINE_LOCATION权限，
        // 以获取更好的广告推荐效果，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdManagerHolder.get().requestPermissionIfNecessary(this);

    }

    private void bindButton(@IdRes int id, final Class clz) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, clz));
            }
        });
    }

    @Override
    public void onBackPressed() {
        boolean isShowInstallDialog = TTAdManagerHolder.get().tryShowInstallDialogWhenExit(this, new ExitInstallListener() {
            @Override
            public void onExitInstall() {
                finish();
            }
        });

        if (!isShowInstallDialog) {
            //没有弹出安装对话框时交由系统处理或者自己的业务逻辑
            super.onBackPressed();
        }
    }
}
