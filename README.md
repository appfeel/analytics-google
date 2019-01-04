Google Analytics Firebase Plugin
================================
Phonegap, Cordova, Ionic, Intel XDK Google Analytics Firebase plugin for iOS/Android with full support for views, events, timings, ecommerce and social.
Ionic Native on the way.

**WARNING**: the previous version (1.5) is broken as Google no longer supports native app analytics without Firebase

**Prerequisites**:

* A Cordova 3.0+ project for iOS and/or Android
* A Firebase project
* Download Google-services.json (android) and GoogleService-info.plist (ios) from Firebase console
*Note:* [Ionic Demo Project](https://gihub.com/appfeel/ionic-admob-analytics-push-quickstarter) on the way.

---
## Platform SDK supported
* iOS, using Google Analytics Firebase
* Android, using Google Analytics Firebase

---
## Quick start
To install this plugin, follow the [Command-line Interface Guide](http://cordova.apache.org/docs/en/edge/guide_cli_index.md.html#The%20Command-line%20Interface). You can use the following command line:

* `cordova plugin add cordova-plugin-analytics --save`

Make sure to review the Google Analytics [terms](http://www.google.com/analytics/terms/us.html) and [SDK Policy](https://developers.google.com/analytics/devguides/collection/protocol/policy)

---
## Javascript API
*Note*: All success callbacks are in the form `'function () {}'`, and all failure callbacks are in the form `'function (err) {}'` where `err` is a String explaining the error reason.

* {function()} `success`: (Optional) success callback.
* {function()} `failure`: (Optional) failure callback.

### setCurrentScreen(screenName, success, error)
Sets the current screen name, which specifies the current visual context in your app. This helps identify the areas in your app where users spend their time and how they interact with your app. (https://firebase.google.com/docs/reference/android/com/google/firebase/analytics/FirebaseAnalytics.html#setCurrentScreen)

* {String} `screenName`: (Required) the name of the screen to track.

*Note*: All screens classes will be tracked as MainActivity in android, and MainViewController in ios, as it is cordova default. You should filter by screen names. It is not possible to change screen class.

### logEvent(eventName, options, success, failure)
Logs an app event. The event can have up to 25 parameters.
Events with the same name must have the same parameters.
Up to 500 event names are supported.
Using predefined [FirebaseAnalytics.Event](https://firebase.google.com/docs/reference/android/com/google/firebase/analytics/FirebaseAnalytics.Event.html) and/or [FirebaseAnalytics.Param](https://firebase.google.com/docs/reference/android/com/google/firebase/analytics/FirebaseAnalytics.Param.html) is recommended for optimal reporting. (https://firebase.google.com/docs/reference/android/com/google/firebase/analytics/FirebaseAnalytics.html#logEvent)

* {String} `eventName`: (Required) the name of the event to track (custom or predefined, see below).
* {Object} `options`: (Optional) JSON object with additional options (see options).

**eventName**: predefined events available see Firebase:

| Type | Event name | Description |
| ---- | ---------- | ----------- |
| String | ADD_PAYMENT_INFO | Add Payment Info event. |
| String | ADD_TO_CART | E-Commerce Add To Cart event. |
| String | ADD_TO_WISHLIST | E-Commerce Add To Wishlist event. |
| String | APP_OPEN | App Open event. |
| String | BEGIN_CHECKOUT | E-Commerce Begin Checkout event. |
| String | CAMPAIGN_DETAILS | Log this event to supply the referral details of a re-engagement campaign. |
| String | CHECKOUT_PROGRESS | Checkout progress. |
| String | EARN_VIRTUAL_CURRENCY | Earn Virtual Currency event. |
| String | ECOMMERCE_PURCHASE | E-Commerce Purchase event. |
| String | GENERATE_LEAD | Generate Lead event. |
| String | JOIN_GROUP | Join Group event. |
| String | LEVEL_END | Level End event. |
| String | LEVEL_START | Level Start event. |
| String | LEVEL_UP | Level Up event. |
| String | LOGIN | Login event. |
| String | POST_SCORE | Post Score event. |
| String | PRESENT_OFFER | Present Offer event. |
| String | PURCHASE_REFUND | E-Commerce Purchase Refund event. |
| String | REMOVE_FROM_CART | Remove from cart event. |
| String | SEARCH | Search event. |
| String | SELECT_CONTENT | Select Content event. |
| String | SET_CHECKOUT_OPTION | Set checkout option. |
| String | SHARE | Share event. |
| String | SIGN_UP | Sign Up event. |
| String | SPEND_VIRTUAL_CURRENCY | Spend Virtual Currency event. |
| String | TUTORIAL_BEGIN | Tutorial Begin event. |
| String | TUTORIAL_COMPLETE | Tutorial End event. |
| String | UNLOCK_ACHIEVEMENT | Unlock Achievement event. |
| String | VIEW_ITEM | View Item event. |
| String | VIEW_ITEM_LIST | View Item List event. |
| String | VIEW_SEARCH_RESULTS | View Search Results event. |
Way to use predefined events:

```js
analytics.logEvent(analytics.DEFAULT_EVENTS.ADD_PAYMENT_INFO, {});
```

**eventParams**: predefined params, see Firebase Params:

| Type | Param name | Description |
| ---- | ---------- | ----------- |
| String | ACHIEVEMENT_ID | Game achievement ID (String). |
| String | ACLID | CAMPAIGN_DETAILS click ID. |
| String | AFFILIATION | The store or affiliation from which this transaction occurred. |
| String | CAMPAIGN | CAMPAIGN_DETAILS name; used for keyword analysis to identify a specific product promotion or strategic campaign. |
| String | CHARACTER | Character used in game (String). |
| String | CHECKOUT_OPTION | Some option on a step in an ecommerce flow. |
| String | CHECKOUT_STEP | The checkout step (1..N). |
| String | CONTENT | CAMPAIGN_DETAILS content; used for A/B testing and content-targeted ads to differentiate ads or links that point to the same URL. |
| String | CONTENT_TYPE | Type of content selected (String). |
| String | COUPON | Coupon code for a purchasable item (String). |
| String | CP1 | CAMPAIGN_DETAILS custom parameter. |
| String | CREATIVE_NAME | The name of a creative used in a promotional spot. |
| String | CREATIVE_SLOT | The name of a creative slot. |
| String | CURRENCY | Purchase currency in 3 letter ISO_4217 format (String). |
| String | DESTINATION | Flight or Travel destination (String). |
| String | END_DATE | The arrival date, check-out date, or rental end date for the item (String). |
| String | FLIGHT_NUMBER | Flight number for travel events (String). |
| String | GROUP_ID | Group/clan/guild id (String). |
| String | INDEX | The index of an item in a list. |
| String | ITEM_BRAND | Item brand. |
| String | ITEM_CATEGORY | Item category (String). |
| String | ITEM_ID | Item ID (String). |
| String | ITEM_LIST | The list in which the item was presented to the user. |
| String | ITEM_LOCATION_ID | The Google Place ID that corresponds to the associated item (String). |
| String | ITEM_NAME | Item name (String). |
| String | ITEM_VARIANT | Item variant. |
| String | LEVEL | Level in game (long). |
| String | LEVEL_NAME | The name of a level in a game (String). |
| String | LOCATION | Location (String). |
| String | MEDIUM | CAMPAIGN_DETAILS medium; used to identify a medium such as email or cost-per-click (cpc). |
| String | METHOD | A particular approach used in an operation; for example, "facebook" or "email" in the context of a sign_up or login event. |
| String | NUMBER_OF_NIGHTS | Number of nights staying at hotel (long). |
| String | NUMBER_OF_PASSENGERS | Number of passengers traveling (long). |
| String | NUMBER_OF_ROOMS | Number of rooms for travel events (long). |
| String | ORIGIN | Flight or Travel origin (String). |
| String | PRICE | Purchase price (double). |
| String | QUANTITY | Purchase quantity (long). |
| String | SCORE | Score in game (long). |
| String | SEARCH_TERM | The search string/keywords used (String). |
| String | SHIPPING | Shipping cost (double). |
| String | SIGN_UP_METHOD | This constant was deprecated. Use METHOD instead. |
| String | SOURCE | CAMPAIGN_DETAILS source; used to identify a search engine, newsletter, or other source. |
| String | START_DATE | The departure date, check-in date, or rental start date for the item (String). |
| String | SUCCESS | The result of an operation (long). |
| String | TAX | Tax amount (double). |
| String | TERM | CAMPAIGN_DETAILS term; used with paid search to supply the keywords for ads. |
| String | TRANSACTION_ID | A single ID for a ecommerce group transaction (String). |
| String | TRAVEL_CLASS | Travel class (String). |
| String | VALUE | A context-specific numeric value which is accumulated automatically for each event type. |
| String | VIRTUAL_CURRENCY_NAME | Name of virtual currency type (String). |

Way to use predefined params:

```js
const params = {};
params[analytics.DEFAULT_PARAMS.CURRENCY] = 'Euro';
params['my param'] = 'my value';
analytics.logEvent(analytics.DEFAULT_EVENTS.ADD_PAYMENT_INFO, params);
```

### resetAnalyticsData(success, error)
Clears all analytics data for this app from the device and resets the app instance id. (https://firebase.google.com/docs/reference/android/com/google/firebase/analytics/FirebaseAnalytics.html#resetAnalyticsData)

### setAnalyticsCollectionEnabled(success, error)
Sets whether analytics collection is enabled for this app on this device. This setting is persisted across app sessions. By default it is enabled. (https://firebase.google.com/docs/reference/android/com/google/firebase/analytics/FirebaseAnalytics.html#setAnalyticsCollectionEnabled)

### setMinimumSessionDuration(success, error)
Sets the minimum engagement time required before starting a session. The default value is 10000 (10 seconds). (https://firebase.google.com/docs/reference/android/com/google/firebase/analytics/FirebaseAnalytics.html#setMinimumSessionDuration)

### setSessionTimeoutDuration(milliseconds, success, error)
Sets the duration of inactivity that terminates the current session. The default value is 1800000 (30 minutes). (https://firebase.google.com/docs/reference/android/com/google/firebase/analytics/FirebaseAnalytics.html#setSessionTimeoutDuration)

* {number} `milliseconds`: (Required) The timeout in milliseconds

### setUserId(userId, success, error)
Sets the user ID property. This feature must be used in accordance with Google's Privacy Policy. (https://firebase.google.com/docs/reference/android/com/google/firebase/analytics/FirebaseAnalytics.html#setUserId)

* {string} `userId`: (Required) The user id

### setUserProperty(userPropertyName, userPropertyValue, success, error)
Sets a user property to a given value. Up to 25 user property names are supported. Once set, user property values persist throughout the app lifecycle and across sessions. (https://firebase.google.com/docs/reference/android/com/google/firebase/analytics/FirebaseAnalytics.html#setUserProperty)

* {string} `serPropertyName`: (Required) The user property name
* {string} `userPropertyValue`: (Required) The user property value
