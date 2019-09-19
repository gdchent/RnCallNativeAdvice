package com.view;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import javax.annotation.Nonnull;

/**
 * 实现思路:我现在是要把原生的组件给RN调用
 */
public class TimerViewManager extends SimpleViewManager<TimerView> {

    @Nonnull
    @Override
    public String getName() {
        return "TimerView";
    }

    @Nonnull
    @Override
    protected TimerView createViewInstance(@Nonnull ThemedReactContext reactContext) {
        TimerView timerView=new TimerView(reactContext);
        return timerView;
    }

    @ReactProp(name = "start")
    public void startTimer(TimerView timerView, Integer totalCount) {
        Log.i("gdchent","倒计时触发");
        timerView.startTimer(totalCount);
    }


}
