/**
 * Created by Yfuks on 09/12/2015.
 *
 * This example show you how to register your current device in your parse app
 * and to subscribe it to a given channel
 *
 * In this example we will register our device and subscribe it to 'global'
 * channel
 */

var React = require('react-native');
var {
  Platform
  } = React;
var ParseManagerAndroid = require('NativeModules').NotificationAndroidManager;

if (Platform.OS === 'android') {
  // We register our device in ou parse app
  ParseManagerAndroid.authenticate((err) => {
    if (err) {
      return console.log(err);
    }
    // We subscribe it to 'global' channel
    ParseManagerAndroid.subscribeToChannel('global', (err) => {
      if (err) {
        return console.log(err);
      }
    });
  });
}