package com.viewmanager;

import android.widget.FrameLayout;

import com.control.BannerAdvice;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.view.BannerLinearLayout;

import javax.annotation.Nonnull;
/**
 * 我这里需要暴露一个Framelayout帧布局给RN这边
 */
public class BannerContainerManager extends SimpleViewManager<BannerLinearLayout> {


    @Nonnull
    @Override
    public String getName() {
        return "BannerContainer";
    }



    @Nonnull
    @Override
    protected BannerLinearLayout createViewInstance(@Nonnull ThemedReactContext reactContext) {
        return BannerAdvice.getInstance(reactContext).getBannerContainer();
    }

}
