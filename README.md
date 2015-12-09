# react-native-parse-notification-android
A wraper around parse to manage android devices

## Prerequisite
1. Create an account on https://www.parse.com/
2. Create an app with your account

## Install
```gradle
// file: android/settings.gradle
...

include ':react-native-parse-notification-android'
project(':react-native-parse-notification-android').projectDir = new File(settingsDir, '../node_modules/react-native-parse-notification-android/android')
```

```gradle
// file: android/app/build.gradle
...

dependencies {
  ...
  compile project(':react-native-parse-notification-android')
  compile 'com.parse.bolts:bolts-android:1.+'
  compile 'com.parse:parse-android:1.+'
}
```
```java
// file: android/app/src/main/java/com/myapp/MainActivity.java
...
import com.notificationandroid.NotificationAndroidPackage; // import
import com.parse.Parse; // import

public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {

    private ReactInstanceManager mReactInstanceManager;
    private ReactRootView mReactRootView;

    // declare package
    private NotificationAndroidPackage mNotificationAndroid;
    // initialize parse (replace both id with coresponding values)
    Parse.initialize(this, "MY-APP-ID", "MY-CLIENT-ID");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReactRootView = new ReactRootView(this);

        // instantiate package
        mNotificationAndroid = new NotificationAndroidPackage(this);

        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setBundleAssetName("index.android.bundle")
                .setJSMainModuleName("index.android")
                .addPackage(new MainReactPackage())

                // register package here
                .addPackage(mNotificationAndroid)

                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();
        mReactRootView.startReactApplication(mReactInstanceManager, "AwesomeProject", null);
        setContentView(mReactRootView);
    }
...
```
```java
// file: android/app/src/main/AndroidManifest.xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.myapp">
  
  // Add the folowing => {
  <uses-permission android:name="android.permission.INTERNET" />
  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
  <uses-permission android:name="android.permission.WAKE_LOCK" />
  <uses-permission android:name="android.permission.VIBRATE" />
  <uses-permission android:name="android.permission.GET_ACCOUNTS" />
  <uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
  <permission android:protectionLevel="signature"
              android:name="com.myapp.permission.C2D_MESSAGE" />   // IMPORTANT replace "com.myapp" with your app package name !
  <uses-permission android:name="com.myapp.permission.C2D_MESSAGE" /> // IMPORTANT replace "com.myapp" with your app package name !
  // }
  ...
  <activity android:name="com.facebook.react.devsupport.DevSettingsActivity" />

  // add the folowing => {
  <service android:name="com.parse.PushService" />
      <receiver android:name="com.parse.ParsePushBroadcastReceiver"
                android:exported="false">
          <intent-filter>
              <action android:name="com.parse.push.intent.RECEIVE" />
              <action android:name="com.parse.push.intent.DELETE" />
              <action android:name="com.parse.push.intent.OPEN" />
          </intent-filter>
      </receiver>
      <receiver android:name="com.parse.GcmBroadcastReceiver"
                android:permission="com.google.android.c2dm.permission.SEND">
          <intent-filter>
              <action android:name="com.google.android.c2dm.intent.RECEIVE" />
              <action android:name="com.google.android.c2dm.intent.REGISTRATION" />

              // IMPORTANT replace "com.myapp" with your app package name !
              <category android:name="com.myapp" />
          </intent-filter>
      </receiver>
    // }
  ...    
```
