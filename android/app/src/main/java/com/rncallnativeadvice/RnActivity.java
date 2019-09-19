package com.rncallnativeadvice;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.bytedance.sdk.openadsdk.TTAdNative;
import com.union_test.toutiao.config.TTAdManagerHolder;
import com.facebook.react.ReactActivity;

public class RnActivity extends ReactActivity {

    public static TTAdNative mTTAdNative;
    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "RnCallNativeAdvice";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTTAdNative = TTAdManagerHolder.get().createAdNative(this);
    }
}
