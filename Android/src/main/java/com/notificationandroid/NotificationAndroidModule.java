package com.notificationandroid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.database.Cursor;
import android.util.Base64;
import android.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseException;
import com.parse.SaveCallback;
import com.parse.ParseUser;

public class NotificationAndroidModule extends ReactContextBaseJavaModule {
  private final ReactApplicationContext mReactContext;
  private final Activity mMainActivity;

  private Callback mCallback;

  public NotificationAndroidModule(ReactApplicationContext reactContext, Activity mainActivity) {
    super(reactContext);

    mReactContext = reactContext;
    mMainActivity = mainActivity;
  }

  /**
   * Authenticate this client as belonging to your application, should be done 
   * before everything
   * 
   * @param applicationId   your application ID
   * @param clientKey       your client KEY
   */
  @ReactMethod
  public void authenticate(final String applicationId, final String clientKey) {
    Parse.initialize(mMainActivity, applicationId, clientKey);
    ParseInstallation.getCurrentInstallation().saveInBackground();
  }

  /**
   * Adds 'channel' to the 'channels' list in the current ParseInstallation
   * and saves it in a background thread.
   * 
   * @param channel         the channel you whant your user to suscribe
   * @param callback
   */
  @ReactMethod
  public void suscribeToChannel(final String channel, final Callback callback) {
    ParsePush.subscribeInBackground(channel, new SaveCallback() {
        @Override
        public void done(ParseException e) {
            if (e == null) {
                callback.invoke(null);
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
                callback.invoke(null);
            } else {
                callback.invoke(e.getMessage());
            }
        }
    });
  }
  
  /**
   * This retrieves the currently logged in ParseUser with a valid session, 
   * either from memory or disk if necessary.
   * 
   * @param field         field to retrieve
   * @param callback
   */
  @ReactMethod
  public void getField(final String field, final Callback callback) {
    callback.invoke((String) ParseInstallation.getCurrentInstallation().get(field));
  }
  
  /**
   * Set the value 'key' to the the current client with the given 'value'
   * 
   * @param key
   * @param value
   */
  @ReactMethod
  public void addField(final String key, final String value) {
    ParseInstallation mInstallation = ParseInstallation.getCurrentInstallation();
    mInstallation.put(key, value);
    mInstallation.saveInBackground();
  }
}