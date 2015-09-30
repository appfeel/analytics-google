#!/usr/bin/env node

// Show info about AdMobAds Plugin

module.exports = function (context) {
  var plugin = context.requireCordovaModule('cordova-lib/src/cordova/plugin'),
    showLegend = true,
    pluginNames = context.opts.cordova.plugins;

  pluginNames.forEach(function (pluginName) {
    if (~pluginNames.indexOf('cordova-admob') || ~pluginNames.indexOf('cordova-admob-ads')) {
      showLegend = false;
    }
  });

  if (showLegend) {
    console.log();
    console.log('*********** Monetize your app with AdMob ads. Now available with this cordova / phonegap plugin:');
    console.log('*********** cordova plugin add cordova-admob');
    console.log('*********** Docs: https://github.com/appfeel/admob-google-cordova');
    console.log();
  }
};