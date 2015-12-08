# react-native-parse-notification-android
A wraper around parse to manage android devices

## Prerequisite
1. Create an account on https://www.parse.com/
2. Create an app

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
}
```
```java
// file: android/app/src/main/java/com/myapp/MainActivity.java
...
import com.imagepicker.NotificationAndroidPackage; // import

public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {

    private ReactInstanceManager mReactInstanceManager;
    private ReactRootView mReactRootView;

    // declare package
    private NotificationAndroidPackage NotificationAndroidPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReactRootView = new ReactRootView(this);

        // instantiate package
        mNotificationAndroid = new ImagePickerPackage(this);

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
