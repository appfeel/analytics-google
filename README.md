Google Analytics Plugin
=======================

Cordova (PhoneGap) 3.0+ Plugin to connect to Google Analytics native SDK.

Prerequisites:
* A Cordova 3.0+ project for iOS and/or Android
* A Mobile App property through the Google Analytics Admin Console

*Note:* [Demo Project](https://github.com/appfeel/analytics-google-demo) available.

---
## Platform SDK supported ##

* iOS, using Google Analytics SDK for iOS, v3.10
* Android, using Google Play Services for Android, v6.1

---
## Quick start ##

To install this plugin, follow the [Command-line Interface Guide](http://cordova.apache.org/docs/en/edge/guide_cli_index.md.html#The%20Command-line%20Interface). You can use one of the following command lines:

* `cordova plugin add cordova-plugin-analytics`
* `cordova plugin add https://github.com/appfeel/analytics-google.git`

Make sure to review the Google Analytics [terms](http://www.google.com/analytics/terms/us.html) and [SDK Policy](https://developers.google.com/analytics/devguides/collection/protocol/policy)

---
## Javascript API ##

*Note:* All success callbacks are in the form `'function () {}'`, and all failure callbacks are in the form `'function (err) {}'` where `err` is a String explaining the error reason.

### Start Google Analytics ###
#### startTrackerWithId(id, success, fail);
Start Analtytics tracker:

* {String}     id:      (Required) your Google Analytics Universal code: UA-XXXXXXX-X.
* {function()} success: (Optional) success callback.
* {function()} failure: (Optional) failure callback.

### Track Views, Events and Timings ###
#### trackView(screenName, options, success, error);
Tracks a screen view.

* {String}     screenName: (Required) the name of the screen to track.
* {Object}     options:    (Optional) JSON object with additional options ([see options](#options)).
* {function()} success:    (Optional) success callback.
* {function()} failure:    (Optional) failure callback.

#### trackEvent(category, action, label, value, options, success, error);
Tracks an event:

* {String}     category: (Required) category of the event.
* {String}     action:   (Required) action of the event.
* {String}     label:    (Required) label of the event.
* {Long}       value:    (Required) value of the event.
* {Object}     options:  (Optional) JSON object with additional options ([see options](#options)).
* {function()} success:  (Optional) success callback.
* {function()} failure:  (Optional) failure callback.

#### trackTiming(category, variable, value, label, options, success, error);
Tracks a timing hit:

* {String}     category: (Required) category of the timing hit.
* {String}     variable: (Required) measured variable (name) of the timing hit.
* {Long}       value:    (Required) value of the timing hit (time in milliseconds).
* {String}     label:    (Optional) label of the timing hit.
* {Object}     options:  (Optional) JSON object with additional options ([see options](#options)).
* {function()} success:  (Optional) success callback.
* {function()} failure:  (Optional) failure callback.

### options
A JSON object contining the following optional values:

* {Object} analytics.OPTIONS.CUSTOM_DIMENSIONS: JSON object with { dimensionIndex: dimensionValue, ... } pairs.
* {Object} analytics.OPTIONS.CUSTOM_METRICS: JSON object with { metricIndex: metricValue, ... } pairs.
* {String} analytics.OPTIONS.CAMPAIGN_URL: Campaign url. Example: http://www.appfeel.com?utm_campaign=analytics&utm_source=github ([see here for more info](https://developer.android.com/reference/com/google/android/gms/analytics/HitBuilders.HitBuilder.html#setCampaignParamsFromUrl(java.lang.String))).

### Track Ecommerce ###
#### trackEcommerce(options, success, error);
Tracks an ecommerce hit.

*Notes:*
- Options are optional if they are optional in Google Analytics SDK.
- You can optionally specify **CUSTOM_DIMENSIONS**, **CUSTOM_METRICS** and/or **CAMPAIGN_URL** in any tracking options object.
- If PRODUCT_SCREEN_NAME, CATEGORY and ACTION values are provided in options, en event will be tracked instead a screen view.
- If PRODUCT_SCREEN_NAME is specified, the tracking action will be senwith PRODUCT_SCREEN_NAME and then the tracker view will be set to previous screen value.

* {Object}     options:  (Optional) JSON object with ecommers options ([see ecommerce options](#ecommerce_examples)).
* {function()} success:  (Optional) success callback.
* {function()} failure:  (Optional) failure callback.

#### trackPromotion(options, success, error);
Track a promotion:

*Notes:*
- Options are optional if they are optional in Google Analytics SDK.
- You can optionally specify **CUSTOM_DIMENSIONS**, **CUSTOM_METRICS** and/or **CAMPAIGN_URL** in any tracking options object.
- If PRODUCT_SCREEN_NAME, CATEGORY and ACTION values are provided in options, en event will be tracked instead a screen view.
- If PRODUCT_SCREEN_NAME is specified, the tracking action will be senwith PRODUCT_SCREEN_NAME and then the tracker view will be set to previous screen value.

* {Object}     options:  (Optional) JSON object with ecommers options ([see ecommerce options](#ecommerce_examples)).
* {function()} success:  (Optional) success callback.
* {function()} failure:  (Optional) failure callback.

#### Ecommerce examples
*Note:* [See Google Documentation](https://developers.google.com/analytics/devguides/collection/ios/v3/enhanced-ecommerce) for a more detailed explanation.

##### Specifying Currency:

    analytics.setCustomParam("&cu", "EUR");

##### Measuring impressions:

```javascript
    var options = {};
    
    options[analytics.OPTIONS.PRODUCT_ID] = "P12345";
    options[analytics.OPTIONS.PRODUCT_NAME] = "AppFeel Warhol T-Shirt";
    options[analytics.OPTIONS.PRODUCT_CATEGORY] = "Apparel/T-Shirts";
    options[analytics.OPTIONS.PRODUCT_BRAND] = "AppFeel";
    options[analytics.OPTIONS.PRODUCT_VARIANT] = "Black";
    options[analytics.OPTIONS.PRODUCT_POSITION] = 2;
    options[analytics.OPTIONS.PRODUCT_SCREEN_NAME] = "My Impression Screen";
    options[analytics.OPTIONS.PRODUCT_QUANTITY] = 2;
    options[analytics.OPTIONS.PRODUCT_PRICE] = 18.99;
    options[analytics.OPTIONS.PRODUCT_LIST] = "Search Results";
    options[analytics.OPTIONS.CUSTOM_DIMENSIONS] = {
      1: "Member"
    };
    options[analytics.OPTIONS.CUSTOM_METRICS] = {
      1: 30,
      4: 1.23
    };
    
    analytics.trackEcommerce(options);
```

##### Measuring actions:

```javascript
    var options = {};
    
    options[analytics.OPTIONS.PRODUCT_ID] = "P12345";
    options[analytics.OPTIONS.PRODUCT_NAME] = "AppFeel Warhol T-Shirt";
    options[analytics.OPTIONS.PRODUCT_CATEGORY] = "Apparel/T-Shirts";
    options[analytics.OPTIONS.PRODUCT_BRAND] = "AppFeel";
    options[analytics.OPTIONS.PRODUCT_VARIANT] = "Black";
    options[analytics.OPTIONS.PRODUCT_POSITION] = 2;
    options[analytics.OPTIONS.PRODUCT_SCREEN_NAME] = "My Impression Screen";
    options[analytics.OPTIONS.PRODUCT_QUANTITY] = 2;
    options[analytics.OPTIONS.PRODUCT_PRICE] = 18.99;
    options[analytics.OPTIONS.PRODUCT_LIST] = "Related Products";
    options[analytics.OPTIONS.PRODUCT_LIST_SOURCE] = "My product list source";
    options[analytics.OPTIONS.PRODUCT_ACTION] = analytics.OPTIONS.PRODUCT_ACTION_CLICK;
    
    analytics.trackEcommerce(options);
```

##### Measuring impressions and actions:

```javascript
    var options = {};
    
    options[analytics.OPTIONS.PRODUCT_ID] = "P12345";
    options[analytics.OPTIONS.PRODUCT_NAME] = "AppFeel Warhol T-Shirt";
    options[analytics.OPTIONS.PRODUCT_CATEGORY] = "Apparel/T-Shirts";
    options[analytics.OPTIONS.PRODUCT_BRAND] = "AppFeel";
    options[analytics.OPTIONS.PRODUCT_VARIANT] = "Black";
    options[analytics.OPTIONS.PRODUCT_POSITION] = 2;
    options[analytics.OPTIONS.PRODUCT_SCREEN_NAME] = "My Impression Screen";
    options[analytics.OPTIONS.PRODUCT_QUANTITY] = 2;
    options[analytics.OPTIONS.PRODUCT_PRICE] = 18.99;
    options[analytics.OPTIONS.PRODUCT_LIST] = "Related Products";
    options[analytics.OPTIONS.PRODUCT_LIST_SOURCE] = "My product list source";
    options[analytics.OPTIONS.PRODUCT_ACTION] = analytics.OPTIONS.PRODUCT_ACTION_DETAIL;
    options[analytics.OPTIONS.RELATED_PRODUCT_ID] = "P12346";
    options[analytics.OPTIONS.RELATED_PRODUCT_NAME] = "AppFeel Warhol T-Shirt";
    options[analytics.OPTIONS.RELATED_PRODUCT_CATEGORY] = "Apparel/T-Shirts";
    options[analytics.OPTIONS.RELATED_PRODUCT_BRAND] = "AppFeel";
    options[analytics.OPTIONS.RELATED_PRODUCT_VARIANT] = "White";
    options[analytics.OPTIONS.RELATED_PRODUCT_POSITION] = 2;
    options[analytics.OPTIONS.RELATED_PRODUCT_SCREEN_NAME] = "My Impression Screen";
    options[analytics.OPTIONS.RELATED_PRODUCT_QUANTITY] = 2;
    options[analytics.OPTIONS.RELATED_PRODUCT_PRICE] = 18.99;
    
    analytics.trackEcommerce(options);
```

##### Measuring add to chart:

```javascript
    var options = {};
    
    options[analytics.OPTIONS.PRODUCT_ID] = "P12345";
    options[analytics.OPTIONS.PRODUCT_NAME] = "AppFeel Warhol T-Shirt";
    options[analytics.OPTIONS.PRODUCT_CATEGORY] = "Apparel/T-Shirts";
    options[analytics.OPTIONS.PRODUCT_BRAND] = "AppFeel";
    options[analytics.OPTIONS.PRODUCT_VARIANT] = "Black";
    options[analytics.OPTIONS.PRODUCT_POSITION] = 2;
    options[analytics.OPTIONS.PRODUCT_SCREEN_NAME] = "My Impression Screen";
    options[analytics.OPTIONS.PRODUCT_QUANTITY] = 2;
    options[analytics.OPTIONS.PRODUCT_PRICE] = 18.99;
    options[analytics.OPTIONS.PRODUCT_COUPON_CODE] = "APPARELSALE";
    options[analytics.OPTIONS.CATEGORY] = "Ecommerce";
    options[analytics.OPTIONS.ACTION] = "+Chart";
    options[analytics.OPTIONS.PRODUCT_ACTION] = analytics.OPTIONS.PRODUCT_ACTION_ADD;
    
    analytics.trackEcommerce(options);
```

##### Measuring remove from chart:

```javascript
    var options = {};
    
    options[analytics.OPTIONS.PRODUCT_ID] = "P12345";
    options[analytics.OPTIONS.PRODUCT_ACTION] = analytics.OPTIONS.PRODUCT_ACTION_REMOVE;
    
    analytics.trackEcommerce(options);
```

##### Measuring checkout process:

```javascript
    var options = {};
    
    options[analytics.OPTIONS.PRODUCT_ID] = "P12345";
    options[analytics.OPTIONS.PRODUCT_NAME] = "AppFeel Warhol T-Shirt";
    options[analytics.OPTIONS.PRODUCT_CATEGORY] = "Apparel/T-Shirts";
    options[analytics.OPTIONS.PRODUCT_BRAND] = "AppFeel";
    options[analytics.OPTIONS.PRODUCT_VARIANT] = "Black";
    options[analytics.OPTIONS.PRODUCT_POSITION] = 2;
    options[analytics.OPTIONS.PRODUCT_SCREEN_NAME] = "My Impression Screen";
    options[analytics.OPTIONS.PRODUCT_QUANTITY] = 2;
    options[analytics.OPTIONS.PRODUCT_PRICE] = 18.99;
    options[analytics.OPTIONS.PRODUCT_COUPON_CODE] = "APPARELSALE";
    options[analytics.OPTIONS.CATEGORY] = "Ecommerce";
    options[analytics.OPTIONS.ACTION] = "Checkout";
    options[analytics.OPTIONS.PRODUCT_ACTION] = analytics.OPTIONS.PRODUCT_ACTION_CHECKOUT;
    options[analytics.OPTIONS.CHECKOUT_STEP] = 1;
    options[analytics.OPTIONS.CHECKOUT_OPTION] = "Visa";
    
    analytics.trackEcommerce(options);
```

##### Measure a changed checkout option:

*Note:* You should not set any product or impression values.

```javascript
    var options = {};
    
    options[analytics.OPTIONS.CATEGORY] = "Ecommerce";
    options[analytics.OPTIONS.ACTION] = "CheckoutOption";
    options[analytics.OPTIONS.PRODUCT_ACTION] = analytics.OPTIONS.PRODUCT_ACTION_CHECKOUT_OPTION;
    options[analytics.OPTIONS.CHECKOUT_STEP] = 1;
    options[analytics.OPTIONS.CHECKOUT_OPTION] = "Fedex";
    
    analytics.trackEcommerce(options);
```

##### Measuring transactions:

```javascript
    var options = {};
    
    options[analytics.OPTIONS.PRODUCT_ID] = "P12345";
    options[analytics.OPTIONS.PRODUCT_NAME] = "AppFeel Warhol T-Shirt";
    options[analytics.OPTIONS.PRODUCT_CATEGORY] = "Apparel/T-Shirts";
    options[analytics.OPTIONS.PRODUCT_BRAND] = "AppFeel";
    options[analytics.OPTIONS.PRODUCT_VARIANT] = "Black";
    options[analytics.OPTIONS.PRODUCT_POSITION] = 2;
    options[analytics.OPTIONS.PRODUCT_SCREEN_NAME] = "My Impression Screen";
    options[analytics.OPTIONS.PRODUCT_QUANTITY] = 2;
    options[analytics.OPTIONS.PRODUCT_PRICE] = 18.99;
    options[analytics.OPTIONS.PRODUCT_COUPON_CODE] = "APPARELSALE";
    options[analytics.OPTIONS.PRODUCT_LIST] = "Search Results";
    options[analytics.OPTIONS.PRODUCT_ID] = "P12346";
    options[analytics.OPTIONS.PRODUCT_NAME] = "AppFeel Warhol T-Shirt";
    options[analytics.OPTIONS.PRODUCT_CATEGORY] = "Apparel/T-Shirts";
    options[analytics.OPTIONS.PRODUCT_BRAND] = "AppFeel";
    options[analytics.OPTIONS.PRODUCT_VARIANT] = "White";
    options[analytics.OPTIONS.PRODUCT_POSITION] = 2;
    options[analytics.OPTIONS.PRODUCT_SCREEN_NAME] = "My Impression Screen";
    options[analytics.OPTIONS.PRODUCT_QUANTITY] = 2;
    options[analytics.OPTIONS.PRODUCT_PRICE] = 18.99;
    options[analytics.OPTIONS.PRODUCT_COUPON_CODE] = "APPARELSALE";
    options[analytics.OPTIONS.PRODUCT_LIST] = "Search Results";
    options[analytics.OPTIONS.CATEGORY] = "Ecommerce";
    options[analytics.OPTIONS.ACTION] = "Purchase";
    options[analytics.OPTIONS.PRODUCT_ACTION] = analytics.OPTIONS.PRODUCT_ACTION_PURCHASE;
    options[analytics.OPTIONS.TRANSACTION_ID] = "T12345";
    options[analytics.OPTIONS.TRANSACTION_AFFILIATION] = "AppFeel Store - Online";
    options[analytics.OPTIONS.TRANSACTION_REVENUE] = 13.29;
    options[analytics.OPTIONS.TRANSACTION_TAX] = 5.70;
    options[analytics.OPTIONS.TRANSACTION_SHIPPING] = 5.00;
    options[analytics.OPTIONS.TRANSACTION_COUPON_CODE] = "SUMMER2014";
    
    analytics.trackEcommerce(options);
```
    
##### Measuring refunds:

Partial refund:
```javascript
    var options = {};
    
    options[analytics.OPTIONS.PRODUCT_ID] = "P12346";
    options[analytics.OPTIONS.PRODUCT_QUANTITY] = 1;
    options[analytics.OPTIONS.CATEGORY] = "Ecommerce";
    options[analytics.OPTIONS.ACTION] = "Refund";
    options[analytics.OPTIONS.PRODUCT_ACTION] = analytics.OPTIONS.PRODUCT_ACTION_REFUND;
    options[analytics.OPTIONS.TRANSACTION_ID] = "T12345";
    
    analytics.trackEcommerce(options);
```

Entire transaction:
```javascript
    var options = {};
    
    options[analytics.OPTIONS.CATEGORY] = "Ecommerce";
    options[analytics.OPTIONS.ACTION] = "Refund";
    options[analytics.OPTIONS.PRODUCT_ACTION] = analytics.OPTIONS.PRODUCT_ACTION_REFUND;
    options[analytics.OPTIONS.TRANSACTION_ID] = "T12345";
    
    analytics.trackEcommerce(options);
```

##### Measuring promotion impressions:

```javascript
    var options = {};
    
    options[analytics.OPTIONS.CATEGORY] = "Ecommerce";
    options[analytics.OPTIONS.ACTION] = "Promotion-view";
    options[analytics.OPTIONS.PROMOTION_ID] = "PROMO_1234 ";
    options[analytics.OPTIONS.PROMOTION_NAME] = "Summer Sale";
    options[analytics.OPTIONS.PROMOTION_CREATIVE] = "summer_banner2";
    options[analytics.OPTIONS.PROMOTION_POSITION] = "banner_slot1";
    
    analytics.trackPromotion(options);
```

##### Measuring promotion clicks:

```javascript
    var options = {};
    
    options[analytics.OPTIONS.CATEGORY] = "Ecommerce";
    options[analytics.OPTIONS.ACTION] = "Promotion-click";
    options[analytics.OPTIONS.LABEL] = "Summer Sale";
    options[analytics.OPTIONS.PROMOTION_ID] = "PROMO_1234 ";
    options[analytics.OPTIONS.PROMOTION_NAME] = "Summer Sale";
    options[analytics.OPTIONS.PROMOTION_CREATIVE] = "summer_banner2";
    options[analytics.OPTIONS.PROMOTION_POSITION] = "banner_slot1";
    options[analytics.OPTIONS.PRODUCT_ACTION] = analytics.OPTIONS.PRODUCT_ACTION_CLICK;
    
    analytics.trackPromotion(options);
```
*Note:* **PRODUCT_ACTION** and **PRODUCT_ACTION_CLICK** are not typo errors.

### Social
#### trackSocial(network, action, target, options, success, error);
Tracks a timing hit:

* {String}     network: (Required) The social network with which the user is interacting (e.g. Facebook, Google+, Twitter, etc.).
* {String}     action:  (Required) The social action taken (e.g. Like, Share, +1, etc.).
* {String}     target:  (Required) The content on which the social action is being taken (i.e. a specific article or video).
* {Object}     options: (Optional) JSON object with additional options ([see options](#options)).
* {function()} success: (Optional) success callback.
* {function()} failure: (Optional) failure callback.

Example:
```javascript
analytics.trackSocial("Facebook ", "Like", "faceboock.com/AppFeel.Inc");
```

### Configuration ###
#### setDispatchPeriod(period, success, error);
Specifies the dispatch period:

* {Number}     period:   (Required) Dispatch period in seconds.
* {function()} success:  (Optional) success callback.
* {function()} failure:  (Optional) failure callback.

#### setAppParams(options, success, error);
Override default app params:

* {Object}     options:  (Required) JSON object with app params (see below).
* {function()} success:  (Optional) success callback.
* {function()} failure:  (Optional) failure callback.

Example (all fields in options object are optional):
```javascript
analytics.setAppParams({
  analytics.OPTIONS.APP_ID: "com.myapp.me",
  analytics.OPTIONS.APP_INSTALLER_ID: "com.appfeel.app",
  analytics.OPTIONS.APP_NAME: "my app name",
  analytics.OPTIONS.APP_VERSION: "1.2.1",
});
```

#### setCampaignFromUrl(url, success, error);
Set campaign url:

* {Object}     url:     (Required) Campaign url. Example: http://www.appfeel.com?utm_campaign=com.analytics.google&utm_source=github&utm_content=setCampaignFromUrl&utm_medium=link ([see here for more info](https://developer.android.com/reference/com/google/android/gms/analytics/HitBuilders.HitBuilder.html#setCampaignParamsFromUrl(java.lang.String))).
* {function()} success: (Optional) success callback.
* {function()} failure: (Optional) failure callback.

#### setCustomParams(options, success, error);
Set any custom parameters you wish (append an & - ampersand - before each key):

* {Object}     options:  (Required) JSON object with custom params params ([see here](https://developers.google.com/analytics/devguides/collection/protocol/v1/parameters) for more info).
* {function()} success:  (Optional) success callback.
* {function()} failure:  (Optional) failure callback.

Example:
```javascript
analytics.setCustomParams({
  "&cn": "dec2014",
  "&cs": "http://appfeel.com"
});
```

#### setCustomParam(key, value, success, error);
Set any custom param you wish (append an & - ampersand - before each key):

* {String}     key:     (Required) parameter name ([see here](https://developers.google.com/analytics/devguides/collection/protocol/v1/parameters) for more info).
* {String}     value:   (Required) value for parameter.
* {function()} success: (Optional) success callback.
* {function()} failure: (Optional) failure callback.

#### setUserId(id, success, error);
Specify user id (improves cross-device tracking).

* {String}     id:      (Required) user id.
* {function()} success: (Optional) success callback.
* {function()} failure: (Optional) failure callback.

#### setDebugMode(success, error);
Sets log level to VERBOSE.

* {function()} success: (Optional) success callback.
* {function()} failure: (Optional) failure callback.