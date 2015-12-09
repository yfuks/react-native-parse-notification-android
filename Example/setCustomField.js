/**
 * Created by Yfuks on 09/12/2015.
 *
 * This example show you how to set a custom field to the current device row.
 * We assume that the device is already authenticate and we also assume that you
 * add the given field in Installation Class
 *
 * In this example we will set the 'username' field on the current device row.
 */

var React = require('react-native');
var {
  Platform
  } = React;
var ParseManagerAndroid = require('NativeModules').NotificationAndroidManager;

if (Platform.OS === 'android') {
  // We get the current device object id to construct the url
  ParseManagerAndroid.getId((id) => {
    var url = 'https://api.parse.com/1/installations/' + id;

    fetch(url, {
      method: 'put',
      headers: {
        'Accept': 'application/json',
        'X-Parse-Application-Id': 'My-Application-ID', // Replace with your Application ID
        'X-Parse-Master-Key': 'My-Master-Key', // Replace with your Master Key
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        username: 'patrick'
      })
    })
      .then((response) => console.log(response))
      .catch((err) => console.log('Error while setting username filed : ' + err));
  });
}