package com.rncallnativeadvice;

import android.app.Application;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.locker.service.LockerService;
import com.locker.service.TraceService;
import com.locker.task.ExecuteTaskManager;
import com.module.AdViceModule;
import com.publicshpackage.AdVicePackage;
import com.publicshpackage.BannerCotainerPackage;
import com.publicshpackage.TimerViewPackage;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.union_test.toutiao.config.TTAdManagerHolder;
import com.xdandroid.hellodaemon.DaemonEnv;

import java.util.ArrayList;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {


    public static RefWatcher sRefWatcher = null;
    public static String PROCESS_NAME_XXXX = "process_name_xxxx";
    private static AdVicePackage adVicePackage = new AdVicePackage();

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            @SuppressWarnings("UnnecessaryLocalVariable")
            List<ReactPackage> packages = new ArrayList<>();
            packages.add(new MainReactPackage());
            packages.add(adVicePackage);
            packages.add(new TimerViewPackage()); //原生暴露给RN的定时器视图
            packages.add(new BannerCotainerPackage());//原生暴露给RN的广告视图
            // Packages that cannot be autolinked yet can be added manually here, for example:
            // packages.add(new MyReactNativePackage());
            return packages;
        }

        @Override
        protected String getJSMainModuleName() {
            return "index";
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }


    public static AdVicePackage getAdVicePackage(){
        return adVicePackage;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, /* native exopackage */ false);
        initAdviceSdk(); //初始化广告
    }


    private void initAdviceSdk() {
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            sRefWatcher = LeakCanary.install(this);
        }
        //锁屏场景demo相关配置
        LockerService.startService(this);
        ExecuteTaskManager.getInstance().init();
        DaemonEnv.initialize(this, TraceService.class, DaemonEnv.DEFAULT_WAKE_UP_INTERVAL);
        TraceService.sShouldStopService = false;
        DaemonEnv.startServiceMayBind(TraceService.class);

        //穿山甲SDK初始化
        //强烈建议在应用对应的Application#onCreate()方法中调用，避免出现content为null的异常
        TTAdManagerHolder.init(this);
        //如果明确某个进程不会使用到广告SDK，可以只针对特定进程初始化广告SDK的content
        //if (PROCESS_NAME_XXXX.equals(processName)) {
        //   TTAdManagerHolder.init(this)
        //}
    }
}
