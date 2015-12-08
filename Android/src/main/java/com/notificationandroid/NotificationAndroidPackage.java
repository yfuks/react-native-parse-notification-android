package com.notificationandroid;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import android.app.Activity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NotificationAndroidPackage implements ReactPackage {
  private final Activity mMainActivity;
  private NotificationAndroidModule mModuleInstance;

  public NotificationAndroidPackage(Activity mainActivity) {
    this.mMainActivity = mainActivity;
  }

  @Override
  public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
    mModuleInstance = new NotificationAndroidModule(reactContext, mMainActivity);

    return Arrays.<NativeModule>asList(mModuleInstance);
  }

  @Override
  public List<Class<? extends JavaScriptModule>> createJSModules() {
    return Collections.emptyList();
  }

  @Override
  public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
    return Collections.emptyList();
  }
}