package com.module;

import android.app.NativeActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.control.BannerAdvice;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.union_test.toutiao.activity.BannerActivity;
import com.union_test.toutiao.activity.FeedListActivity;
import com.view.BannerContainerManager;

import org.json.JSONException;
import org.json.JSONObject;


public class AdViceModule extends ReactContextBaseJavaModule {

    public static final String MODULE_NAME = "AdViceModule";
    public static final String EVENT_NAME = "nativeCallRn";
    public static final String EVENT_DETAIL_NAME = "nativeCallRnDetail";
    public ReactApplicationContext context;

    public AdViceModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Override
    public void initialize() {
        Log.i("gdchent", "module_initialize");// 呼叫原生最先调用的方法 可以用日志追踪顺序

    }

    @Override
    public boolean canOverrideExistingModule() {
        return false;
    }

    @Override
    public void onCatalystInstanceDestroy() {

    }

    /**
     * Native调用RN
     *
     * @param msg
     */
    public void nativeCallRn(String msg) {
        context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(EVENT_NAME, msg);

    }


    /**
     * 进入RN的详情页面
     */
    public void nativeCallRnDetail(String msg) {
        Log.i("gdchent", "apkUrl" + msg);
        context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(EVENT_DETAIL_NAME, msg);
    }

    /**
     * @param data
     */
    @ReactMethod
    public void rnCallNative(String data) {
        try {
            //原生获取RN的json数据
            JSONObject jsonObject = new JSONObject(data);
            int index = jsonObject.getInt("index");
            Intent intent=null;
            switch (index) { //这里只测试2个
                case 0:
                    //进行逻辑处理
                    BannerAdvice.getInstance(context).showBannerDownload();
                    break;
                case 1:
                    intent = new Intent(context, FeedListActivity.class);
                    context.startActivity(intent);
                    break;
            }

        } catch (JSONException e) {
            Log.i("gdchent", "error:" + e.getMessage());//抛出json数据错误信息
        }finally {

        }
    }


    @ReactMethod
    public void gotoActivity() {
//        Intent intent=new Intent(context, NativeActivity.class);
//        Log.i("gdchent","走向NativeActivity");
//        context.startActivity(intent);
    }

    /**
     * Callback 方式
     * rn调用Native,并获取返回值
     *
     * @param msg
     * @param callback
     */
    @ReactMethod
    public void rnCallNativeFromCallback(String data, Callback callback) {

        // 1.处理业务逻辑...
        String result = "处理结果：" + data;
        Log.i("gdchent", "接收rn传来的数据:" + data);

        // 2.回调RN,即将处理结果返回给RN
        callback.invoke(result);

    }


    /**
     * Promise
     *
     * @param msg
     * @param promise
     */
    @ReactMethod
    public void rnCallNativeFromPromise(String msg, Promise promise) {
        Log.e("---", "adasdasda");
        // 1.处理业务逻辑...
        String result = "处理结果：" + msg;
        // 2.回调RN,即将处理结果返回给RN
        promise.resolve(result);
    }

}
