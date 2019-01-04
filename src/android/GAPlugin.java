/*
 CPGoogleAnalytics.java
 Copyright 2014 AppFeel. All rights reserved.
 http://www.appfeel.com
 
 Google Analytics Cordova Plugin (com.analytics.google)
 
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to
 deal in the Software without restriction, including without limitation the
 rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 sell copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package com.appfeel.cordova.analytics;

import com.appfeel.cordova.annotated.android.plugin.AnnotatedCordovaPlugin;
import com.appfeel.cordova.annotated.android.plugin.ExecutionThread;
import com.appfeel.cordova.annotated.android.plugin.PluginAction;

import java.util.Iterator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class GAPlugin extends AnnotatedCordovaPlugin {

  private static final String OPT_EVENT_NAME = "eventName";
  private static final String OPT_EVENT_PARAMS = "eventParams";
  private static final String OPT_USER_PROPERTY_NAME = "userPropertyName";
  private static final String OPT_USER_PROPERTY_VALUE = "userPropertyValue";

  private FirebaseAnalytics mFirebaseAnalytics = null;

  @PluginAction(thread=ExecutionThread.WORKER, actionName="logEvent", isAutofinish=false)
  private void logEvent(JSONObject options, CallbackContext callbackContext) {
    if (mFirebaseAnalytics == null) {
      mFirebaseAnalytics = FirebaseAnalytics.getInstance(this.cordova.getActivity());
    }
    
    PluginResult result = new PluginResult(Status.ERROR, "Missing event name");

    if (options.has(OPT_EVENT_NAME)) {
      try {
        Bundle bundle = new Bundle();
        if (options.has(OPT_EVENT_PARAMS)) {
          JSONObject params = options.optJSONObject(OPT_EVENT_PARAMS);
          Iterator<String> keys = params.keys();
          while(keys.hasNext()) {
            String key = keys.next();
            bundle.putString(key, params.getString(key));
          }
        }
        mFirebaseAnalytics.logEvent(options.optString(OPT_EVENT_NAME), bundle);
        result = new PluginResult(Status.OK);
      } catch (Exception e) {
        result = new PluginResult(Status.ERROR, e.getMessage());
      }
    }

    callbackContext.sendPluginResult(result);
  }

  @PluginAction(thread=ExecutionThread.WORKER, actionName="resetAnalyticsData", isAutofinish=false)
  private void resetAnalyticsData(CallbackContext callbackContext) {
    if (mFirebaseAnalytics == null) {
      mFirebaseAnalytics = FirebaseAnalytics.getInstance(this.cordova.getActivity());
    }

    PluginResult result = new PluginResult(Status.OK);

    try {
      mFirebaseAnalytics.resetAnalyticsData();
    } catch (Exception e) {
      result = new PluginResult(Status.ERROR, e.getMessage());
    } 
    
    callbackContext.sendPluginResult(result);
  }

  @PluginAction(thread=ExecutionThread.WORKER, actionName="setAnalyticsCollectionEnabled", isAutofinish=false)
  private void setAnalyticsCollectionEnabled(boolean enabled, CallbackContext callbackContext) {
    if (mFirebaseAnalytics == null) {
      mFirebaseAnalytics = FirebaseAnalytics.getInstance(this.cordova.getActivity());
    }

    PluginResult result = new PluginResult(Status.OK);

    try {
      mFirebaseAnalytics.setAnalyticsCollectionEnabled(enabled);
    } catch (Exception e) {
      result = new PluginResult(Status.ERROR, e.getMessage());
    } 
    
    callbackContext.sendPluginResult(result);
  }

  @PluginAction(thread=ExecutionThread.UI, actionName="setCurrentScreen", isAutofinish=false)
  private void setCurrentScreen(String screen, CallbackContext callbackContext) {
    if (mFirebaseAnalytics == null) {
      mFirebaseAnalytics = FirebaseAnalytics.getInstance(this.cordova.getActivity());
    }

    PluginResult result = new PluginResult(Status.ERROR, "Missing current screen name");

    if (screen != null) {
      try {
        mFirebaseAnalytics.setCurrentScreen(this.cordova.getActivity(), screen, null);
        result = new PluginResult(Status.OK);
      } catch (Exception e) {
        result = new PluginResult(Status.ERROR, e.getMessage());
      }
    }

    callbackContext.sendPluginResult(result);
  }

  @PluginAction(thread=ExecutionThread.WORKER, actionName="setMinimumSessionDuration", isAutofinish=false)
  private void setMinimumSessionDuration(long milliseconds, CallbackContext callbackContext) {
    if (mFirebaseAnalytics == null) {
      mFirebaseAnalytics = FirebaseAnalytics.getInstance(this.cordova.getActivity());
    }
    
    PluginResult result = new PluginResult(Status.OK);

    try {
      mFirebaseAnalytics.setMinimumSessionDuration(milliseconds);
    } catch (Exception e) {
      result = new PluginResult(Status.ERROR, e.getMessage());
    } 
    
    callbackContext.sendPluginResult(result);
  }

  @PluginAction(thread=ExecutionThread.WORKER, actionName="setSessionTimeoutDuration", isAutofinish=false)
  private void setSessionTimeoutDuration(long milliseconds, CallbackContext callbackContext) {
    if (mFirebaseAnalytics == null) {
      mFirebaseAnalytics = FirebaseAnalytics.getInstance(this.cordova.getActivity());
    }
    
    PluginResult result = new PluginResult(Status.OK);

    try {
      mFirebaseAnalytics.setSessionTimeoutDuration(milliseconds);
    } catch (Exception e) {
      result = new PluginResult(Status.ERROR, e.getMessage());
    } 
    
    callbackContext.sendPluginResult(result);
  }

  @PluginAction(thread=ExecutionThread.WORKER, actionName="setUserId", isAutofinish=false)
  private void setUserId(String userId, CallbackContext callbackContext) {
    if (mFirebaseAnalytics == null) {
      mFirebaseAnalytics = FirebaseAnalytics.getInstance(this.cordova.getActivity());
    }
    
    PluginResult result = new PluginResult(Status.ERROR, "Missing user id");

    if (userId != null) {
      try {
        mFirebaseAnalytics.setUserId(userId);
        result = new PluginResult(Status.OK);
      } catch (Exception e) {
        result = new PluginResult(Status.ERROR, e.getMessage());
      }
    }

    callbackContext.sendPluginResult(result);
  }

  @PluginAction(thread=ExecutionThread.WORKER, actionName="setUserProperty", isAutofinish=false)
  private void setUserProperty(JSONObject options, CallbackContext callbackContext) {
    if (mFirebaseAnalytics == null) {
      mFirebaseAnalytics = FirebaseAnalytics.getInstance(this.cordova.getActivity());
    }
    
    PluginResult result = new PluginResult(Status.ERROR, "Missing user property name or user property value");

    if (options.has(OPT_USER_PROPERTY_NAME) && options.has(OPT_USER_PROPERTY_VALUE)) {
      try {
        mFirebaseAnalytics.setUserProperty(options.optString(OPT_USER_PROPERTY_NAME), options.optString(OPT_USER_PROPERTY_VALUE));
        result = new PluginResult(Status.OK);
      } catch (Exception e) {
        result = new PluginResult(Status.ERROR, e.getMessage());
      }
    }

    callbackContext.sendPluginResult(result);
  }

}
