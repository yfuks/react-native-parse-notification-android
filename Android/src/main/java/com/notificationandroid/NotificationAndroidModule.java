package com.notificationandroid;

import android.app.Activity;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseException;
import com.parse.SaveCallback;

import android.util.Log;

public class NotificationAndroidModule extends ReactContextBaseJavaModule {
  private final ReactApplicationContext mReactContext;
  private final Activity mMainActivity;

  private Callback mCallback;

  public NotificationAndroidModule(ReactApplicationContext reactContext, Activity mainActivity) {
    super(reactContext);

    mReactContext = reactContext;
    mMainActivity = mainActivity;
  }

  @Override
  public String getName() {
    return "NotificationAndroidManager";
  }
  
  /**
   * Authenticate this client as belonging to your application, should be done 
   * before everything
   * 
   * @param callback
   */
  @ReactMethod
  public void authenticate(final Callback callback) {
    ParseInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
          @Override
          public void done(ParseException e) {
              if (e == null) {
                  callback.invoke();
              } else {
                  callback.invoke(e.getMessage());
              }
          }
     });
  }
  
  /**
   * Adds 'channel' to the 'channels' list in the current ParseInstallation
   * and saves it in a background thread.
   * 
   * @param channel         the channel you whant your user to suscribe
   * @param callback
   */
  @ReactMethod
  public void subscribeToChannel(final String channel, final Callback callback) {
    ParsePush.subscribeInBackground(channel, new SaveCallback() {
        @Override
        public void done(ParseException e) {
            if (e == null) {
                callback.invoke();
            } else {
                callback.invoke(e.getMessage());
            }
        }
    });
  }

  /**
   * Removes 'channel' to the 'channels' list in the current ParseInstallation
   * and saves it in a background thread.
   * 
   * @param channel         the channel you whant your user to unsuscribe
   * @param callback
   */
  @ReactMethod
  public void unsubscribeToChannel(final String channel, final Callback callback) {
      ParsePush.unsubscribeInBackground(channel, new SaveCallback() {
        @Override
        public void done(ParseException e) {
            if (e == null) {
                callback.invoke();
            } else {
                callback.invoke(e.getMessage());
            }
        }
    });
  }

  @ReactMethod
  public void getId(final Callback callback) {
    callback.invoke((String) ParseInstallation.getCurrentInstallation().getObjectId());
  }
  
  /**
   * This retrieves the current value of field as a string
   * 
   * @param field         field to retrieve
   * @param callback
   */
  @ReactMethod
  public void getString(final String field, final Callback callback) {
    ParseInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
        @Override
        public void done(ParseException e) {
            if (e == null) {
                callback.invoke(null, (String) ParseInstallation.getCurrentInstallation().get(field));
            } else {
                callback.invoke(e.getMessage());
            }
        }
    });
  }
}