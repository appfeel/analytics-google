/*
 analytics.js
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

var analytics = window.analytics || {};

analytics.OPTIONS = {
  SCREEN_NAME: "screenName",
  CATEGORY: "category",
  ACTION: "action",
  LABEL: "label",
  VALUE: "value",
  VARIABLE: "variable",
  NETWORK: "network",
  TARGET: "target",

  CUSTOM_DIMENSIONS: "customDimensions",
  CUSTOM_METRICS: "customMetrics",
  CAMPAIGN_URL: "campaignUrl",

  APP_ID: "appId",
  APP_INSTALLER_ID: "appInstallerId",
  APP_NAME: "appName",
  APP_VERSION: "appVersion",

  PRODUCT_ID: "productId",
  PRODUCT_NAME: "productName",
  PRODUCT_CATEGORY: "productCategory",
  PRODUCT_BRAND: "productBrand",
  PRODUCT_VARIANT: "productVariant",
  PRODUCT_POSITION: "productPosition",
  PRODUCT_SCREEN_NAME: "productScreenName",
  PRODUCT_COUPON_CODE: "productCouponCode",
  PRODUCT_QUANTITY: "productQuantity",
  PRODUCT_PRICE: "productPrice",

  RELATED_PRODUCT_ID: "relatedProductId",
  RELATED_PRODUCT_NAME: "relatedProductName",
  RELATED_PRODUCT_CATEGORY: "relatedProductCategory",
  RELATED_PRODUCT_BRAND: "relatedProductBrand",
  RELATED_PRODUCT_VARIANT: "relatedProductVariant",
  RELATED_PRODUCT_POSITION: "relatedProductPosition",
  RELATED_PRODUCT_COUPON_CODE: "relatedProductCouponCode",
  RELATED_PRODUCT_QUANTITY: "relatedProductQuantity",
  RELATED_PRODUCT_PRICE: "relatedProductPrice",

  PRODUCT_LIST: "productList",
  PRODUCT_LIST_SOURCE: "productListSource",

  PRODUCT_ACTION: "productAction",
  PRODUCT_ACTION_IMPRESSION: "productActionImpression",
  PRODUCT_ACTION_DETAIL: "productActionDetail",
  PRODUCT_ACTION_PURCHASE: "productActionPurchase",
  PRODUCT_ACTION_REFUND: "productActionRefund",
  PRODUCT_ACTION_CHECKOUT: "productActionCheckout",
  PRODUCT_ACTION_CHECKOUT_OPTION: "productActionCheckoutOption",
  PRODUCT_ACTION_CLICK: "productActionClick",
  PRODUCT_ACTION_ADD: "productActionAdd",
  PRODUCT_ACTION_REMOVE: "productActionRemove",

  TRANSACTION_ID: "transactionId",
  TRANSACTION_AFFILIATION: "transactionAffiliation",
  TRANSACTION_REVENUE: "transactionRevenue",
  TRANSACTION_TAX: "transactionTax",
  TRANSACTION_SHIPPING: "transactionShipping",
  TRANSACTION_COUPON_CODE: "transactionCouponCode",

  CHECKOUT_STEP: "checkoutStep",
  CHECKOUT_OPTION: "checkoutOption",

  PROMOTION_ID: "promotionId",
  PROMOTION_NAME: "promotionName",
  PROMOTION_CREATIVE: "promotionCreative",
  PROMOTION_POSITION: "promotionPosition"
};


analytics.startTrackerWithId = function (id, success, error) {
  cordova.exec(success, error, 'GAPlugin', 'startTrackerWithId', [id]);
};

analytics.trackView = function (screenName, options, success, error) {
  if (typeof options === 'function') {
    error = success;
    success = options;
    options = {};
  }

  options = options || {};
  options[analytics.OPTIONS.SCREEN_NAME] = screenName;
  cordova.exec(success, error, 'GAPlugin', 'trackView', [options]);
};

analytics.trackEvent = function (category, action, label, value, options, success, error) {
  if (typeof label === 'function') {
    error = value;
    success = label;
    options = {};
    value = 0;
    label = '';
  }
  if (typeof value === 'function') {
    error = options;
    success = value;
    options = {};
    value = 0;
  }
  if (typeof options === 'function') {
    error = success;
    success = options;
    options = {};
  }

  if (typeof label === 'undefined' || label === null) {
    label = '';
  }
  if (typeof value === 'undefined' || value === null) {
    value = 0;
  }

  options = options || {};
  options[analytics.OPTIONS.CATEGORY] = category;
  options[analytics.OPTIONS.ACTION] = action;
  options[analytics.OPTIONS.LABEL] = label;
  options[analytics.OPTIONS.VALUE] = value;
  
  cordova.exec(success, error, 'GAPlugin', 'trackEvent', [options]);
};

analytics.trackTiming = function (category, variable, value, label, options, success, error) {
  if (typeof label === 'function') {
    error = options;
    success = label;
    options = {};
    label = '';
  }
  if (typeof options === 'function') {
    error = success;
    success = options;
    options = {};
  }

  if (typeof label === 'undefined' || label === null) {
    label = '';
  }
  options = options || {};
  options[analytics.OPTIONS.CATEGORY] = category;
  options[analytics.OPTIONS.VALUE] = value;
  options[analytics.OPTIONS.VARIABLE] = variable;
  options[analytics.OPTIONS.LABEL] = label;

  cordova.exec(success, error, 'GAPlugin', 'trackTiming', [options]);
};

analytics.trackEcommerce = function (options, success, error) {
  cordova.exec(success, error, 'GAPlugin', 'trackEcommerce', [options]);
};

analytics.trackPromotion = function (options, success, error) {
  cordova.exec(success, error, 'GAPlugin', 'trackPromotion', [options]);
};

analytics.trackSocial = function (network, action, target, options, success, error) {
  if (typeof options === 'function') {
    error = success;
    success = options;
    options = {};
  }

  options = options || {};
  options[analytics.OPTIONS.NETWORK] = network;
  options[analytics.OPTIONS.ACTION] = action;
  options[analytics.OPTIONS.TARGET] = target;

  cordova.exec(success, error, 'GAPlugin', 'trackSocial', [options]);
};

analytics.setDispatchPeriod = function (period, success, error) {
  cordova.exec(success, error, 'GAPlugin', 'setDispatchPeriod', [period]);
};

analytics.setAppParams = function (options, success, error) {
  cordova.exec(success, error, 'GAPlugin', 'setAppParams', [options]);
};

analytics.setCampaignFromUrl = function (url, success, error) {
  cordova.exec(success, error, 'GAPlugin', 'setCampaignFromUrl', [url]);
};

analytics.setCustomParams = function (options, success, error) {
  cordova.exec(success, error, 'GAPlugin', 'setCustomParams', [options]);
};

analytics.setCustomParam = function (key, value, success, error) {
  cordova.exec(success, error, 'GAPlugin', 'setCustomParams', [{
    key: value
  }]);
};

analytics.setUserId = function (id, success, error) {
  cordova.exec(success, error, 'GAPlugin', 'setUserId', [id]);
};

analytics.setDebugMode = function (success, error) {
  cordova.exec(success, error, 'GAPlugin', 'setDebugMode', []);
};

window.analytics = analytics;
