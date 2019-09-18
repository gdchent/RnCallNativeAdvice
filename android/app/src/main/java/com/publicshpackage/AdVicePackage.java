package com.publicshpackage;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import com.module.AdViceModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 下载Package
 */
public class AdVicePackage implements ReactPackage {

    public static AdViceModule  adViceModule ;
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules=new ArrayList<>();
        adViceModule=new AdViceModule(reactContext);
        modules.add(adViceModule);
        return modules;
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
