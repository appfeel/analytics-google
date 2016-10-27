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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Logger.LogLevel;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;

public class CPGoogleAnalytics extends CordovaPlugin {
  public static Tracker tracker = null;

  private static final String ACTION_START_TRACKER = "startTrackerWithId";
  private static final String ACTION_SET_DISPATCH_PERIOD = "setDispatchPeriod";
  private static final String ACTION_TRACK_VIEW = "trackView";
  private static final String ACTION_TRACK_EVENT = "trackEvent";
  private static final String ACTION_TRACK_TIMING = "trackTiming";
  private static final String ACTION_TRACK_ECOMMERCE = "trackEcommerce";
  private static final String ACTION_TRACK_PROMOTION = "trackPromotion";
  private static final String ACTION_TRACK_SOCIAL = "trackSocial";

  private static final String ACTION_SET_APP_PARAMS = "setAppParams";
  private static final String ACTION_SET_CAMPAIGN_FROM_URL = "setCampaignFromUrl";
  private static final String ACTION_SET_CUSTOM_PARAMS = "setCustomParams";
  private static final String ACTION_SET_USER_ID = "setUserId";
  private static final String ACTION_SET_DEBUG_MODE = "setDebugMode";

  private static final String OPT_SCREEN_NAME = "screenName";
  private static final String OPT_CATEGORY = "category";
  private static final String OPT_ACTION = "action";
  private static final String OPT_LABEL = "label";
  private static final String OPT_VALUE = "value";
  private static final String OPT_VARIABLE = "variable";
  private static final String OPT_NETWORK = "network";
  private static final String OPT_TARGET = "target";
  private static final String OPT_CUSTOM_DIMENSIONS = "customDimensions";
  private static final String OPT_CUSTOM_METRICS = "customMetrics";
  private static final String OPT_CAMPAIGN_URL = "campaignUrl";

  private static final String OPT_APP_ID = "appId";
  private static final String OPT_APP_INSTALLER_ID = "appInstallerId";
  private static final String OPT_APP_NAME = "appName";
  private static final String OPT_APP_VERSION = "appVersion";

  private static final String OPT_CURRENCY_CODE = "currencyCode";
  private static final String OPT_PRODUCT_ID = "productId";
  private static final String OPT_PRODUCT_NAME = "productName";
  private static final String OPT_PRODUCT_CATEGORY = "productCategory";
  private static final String OPT_PRODUCT_BRAND = "productBrand";
  private static final String OPT_PRODUCT_VARIANT = "productVariant";
  private static final String OPT_PRODUCT_POSITION = "productPosition";
  private static final String OPT_PRODUCT_SCREEN_NAME = "productScreenName";
  private static final String OPT_PRODUCT_COUPON_CODE = "productCouponCode";
  private static final String OPT_PRODUCT_QUANTITY = "productQuantity";
  private static final String OPT_PRODUCT_PRICE = "productPrice";

  private static final String OPT_RELATED_PRODUCT_ID = "relatedProductId";
  private static final String OPT_RELATED_PRODUCT_NAME = "relatedProductName";
  private static final String OPT_RELATED_PRODUCT_CATEGORY = "relatedProductCategory";
  private static final String OPT_RELATED_PRODUCT_BRAND = "relatedProductBrand";
  private static final String OPT_RELATED_PRODUCT_VARIANT = "relatedProductVariant";
  private static final String OPT_RELATED_PRODUCT_POSITION = "relatedProductPosition";
  private static final String OPT_RELATED_PRODUCT_COUPON_CODE = "relatedProductCouponCode";
  private static final String OPT_RELATED_PRODUCT_QUANTITY = "relatedProductQuantity";
  private static final String OPT_RELATED_PRODUCT_PRICE = "relatedProductPrice";

  private static final String OPT_PRODUCT_LIST = "productList";
  private static final String OPT_PRODUCT_LIST_SOURCE = "productListSource";

  private static final String OPT_PRODUCT_ACTION = "productAction";
  private static final String OPT_PRODUCT_ACTION_IMPRESSION = "productActionImpression";
  private static final String OPT_PRODUCT_ACTION_DETAIL = "productActionDetail";
  private static final String OPT_PRODUCT_ACTION_PURCHASE = "productActionPurchase";
  private static final String OPT_PRODUCT_ACTION_REFUND = "productActionRefund";
  private static final String OPT_PRODUCT_ACTION_CHECKOUT = "productActionCheckout";
  private static final String OPT_PRODUCT_ACTION_CHECKOUT_OPTION = "productActionCheckoutOption";
  private static final String OPT_PRODUCT_ACTION_CLICK = "productActionClick";
  private static final String OPT_PRODUCT_ACTION_ADD = "productActionAdd";
  private static final String OPT_PRODUCT_ACTION_REMOVE = "productActionRemove";

  private static final String OPT_TRANSACTION_ID = "transactionId";
  private static final String OPT_TRANSACTION_AFFILIATION = "transactionAffiliation";
  private static final String OPT_TRANSACTION_REVENUE = "transactionRevenue";
  private static final String OPT_TRANSACTION_TAX = "transactionTax";
  private static final String OPT_TRANSACTION_SHIPPING = "transactionShipping";
  private static final String OPT_TRANSACTION_COUPON_CODE = "transactionCouponCode";

  private static final String OPT_CHECKOUT_STEP = "checkoutStep";
  private static final String OPT_CHECKOUT_OPTION = "checkoutOption";

  private static final String OPT_PROMOTION_ID = "promotionId";
  private static final String OPT_PROMOTION_NAME = "promotionName";
  private static final String OPT_PROMOTION_CREATIVE = "promotionCreative";
  private static final String OPT_PROMOTION_POSITION = "promotionPosition";

  private String campaignUrl = null;
  private String actualScreen = "";

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    PluginResult result = null;

    if (ACTION_START_TRACKER.equals(action)) {
      if (args.length() > 0) {
        result = execStartTracker(args.getString(0), callbackContext);
      }

    } else if (ACTION_SET_DISPATCH_PERIOD.equals(action)) {
      if (args.length() > 0) {
        result = execSetDispatchPeriod(args.getInt(0), callbackContext);
      }

    } else if (ACTION_TRACK_VIEW.equals(action)) {
      if (args.length() > 0) {
        result = execTrackView(args.getJSONObject(0), callbackContext);
      }

    } else if (ACTION_TRACK_EVENT.equals(action)) {
      if (args.length() > 0) {
        result = execTrackEvent(args.getJSONObject(0), callbackContext);
      }

    } else if (ACTION_TRACK_TIMING.equals(action)) {
      if (args.length() > 0) {
        result = execTrackTiming(args.getJSONObject(0), callbackContext);
      }

    } else if (ACTION_TRACK_ECOMMERCE.equals(action)) {
      if (args.length() > 0) {
        result = execTrackEcommerce(args.getJSONObject(0), callbackContext);
      }

    } else if (ACTION_TRACK_PROMOTION.equals(action)) {
      if (args.length() > 0) {
        result = execTrackPromotion(args.getJSONObject(0), callbackContext);
      }

    } else if (ACTION_TRACK_SOCIAL.equals(action)) {
      if (args.length() > 0) {
        result = execTrackSocial(args.getJSONObject(0), callbackContext);
      }

    } else if (ACTION_SET_APP_PARAMS.equals(action)) {
      if (args.length() > 0) {
        result = execSetAppParams(args.getJSONObject(0), callbackContext);
      }

    } else if (ACTION_SET_CAMPAIGN_FROM_URL.equals(action)) {
      if (args.length() > 0) {
        result = execSetCampaignFromUrl(args.getString(0), callbackContext);
      }

    } else if (ACTION_SET_CUSTOM_PARAMS.equals(action)) {
      if (args.length() > 0) {
        result = execSetCustomParams(args.getJSONObject(0), callbackContext);
      }

    } else if (ACTION_SET_USER_ID.equals(action)) {
      if (args.length() > 0) {
        result = execSetUserId(args.getString(0), callbackContext);
      }

    } else if (ACTION_SET_DEBUG_MODE.equals(action)) {
      result = execSetDebugMode(callbackContext);

    } else {
      return false;
    }

    if (result != null) {
      callbackContext.sendPluginResult(result);
    }

    return true;
  }

  private PluginResult execStartTracker(String id, CallbackContext callbackContext) {
    PluginResult result = new PluginResult(Status.ERROR, "Invalid tracker");

    if (null != id && id.length() > 0) {
      tracker = GoogleAnalytics.getInstance(this.cordova.getActivity()).newTracker(id);
      GoogleAnalytics.getInstance(this.cordova.getActivity()).setLocalDispatchPeriod(30);
      GoogleAnalytics.getInstance(this.cordova.getActivity()).getLogger().setLogLevel(LogLevel.ERROR);
      result = new PluginResult(Status.OK);
    }

    return result;
  }

  private PluginResult execSetDispatchPeriod(Integer period, CallbackContext callbackContext) {
    PluginResult result = new PluginResult(Status.ERROR, "Missing dispatch period value");

    if (null != period) {
      GoogleAnalytics.getInstance(this.cordova.getActivity()).setLocalDispatchPeriod(period);
      result = new PluginResult(Status.OK);
    }

    return result;
  }

  private PluginResult execTrackView(JSONObject options, CallbackContext callbackContext) {
    PluginResult result = new PluginResult(Status.ERROR, "Missing screen name");

    if (tracker == null) {
      result = new PluginResult(Status.ERROR, "Tracker not started. Call startTrackerWithId('UA-XXXXXX'); first.");

    } else if (options.has(OPT_SCREEN_NAME)) {
      HitBuilders.ScreenViewBuilder builder = new HitBuilders.ScreenViewBuilder();
      prepareBuilder(builder, options);

      actualScreen = options.optString(OPT_SCREEN_NAME);
      tracker.setScreenName(actualScreen);

      tracker.send(builder.build());
      result = new PluginResult(Status.OK);
    }

    return result;
  }

  private PluginResult execTrackEvent(JSONObject options, CallbackContext callbackContext) {
    PluginResult result = new PluginResult(Status.ERROR, "Missing category or action");

    if (tracker == null) {
      result = new PluginResult(Status.ERROR, "Tracker not started. Call startTrackerWithId('UA-XXXXXX'); first.");

    } else if (options.has(OPT_CATEGORY) && options.has(OPT_ACTION)) {
      HitBuilders.EventBuilder builder = new HitBuilders.EventBuilder();
      prepareBuilder(builder, options);

      builder.setCategory(options.optString(OPT_CATEGORY));
      builder.setAction(options.optString(OPT_ACTION));
      if (options.has(OPT_LABEL)) {
        builder.setLabel(options.optString(OPT_LABEL));
      }
      if (options.has(OPT_VALUE)) {
        builder.setValue(options.optLong(OPT_VALUE));
      }

      tracker.send(builder.build());
      result = new PluginResult(Status.OK);
    }

    return result;
  }

  private PluginResult execTrackTiming(JSONObject options, CallbackContext callbackContext) {
    PluginResult result = new PluginResult(Status.ERROR, "Missing category, variable to measure or value");

    if (tracker == null) {
      result = new PluginResult(Status.ERROR, "Tracker not started. Call startTrackerWithId('UA-XXXXXX'); first.");

    } else if (options.has(OPT_CATEGORY) && options.has(OPT_VARIABLE) && options.has(OPT_VALUE)) {
      HitBuilders.TimingBuilder builder = new HitBuilders.TimingBuilder();
      prepareBuilder(builder, options);

      builder.setCategory(options.optString(OPT_CATEGORY));
      builder.setVariable(options.optString(OPT_VARIABLE));
      builder.setValue(options.optLong(OPT_VALUE));
      if (options.has(OPT_LABEL)) {
        builder.setLabel(options.optString(OPT_LABEL));
      }

      tracker.send(builder.build());
      result = new PluginResult(Status.OK);

    }

    return result;
  }

  private PluginResult execTrackEcommerce(JSONObject options, CallbackContext callbackContext) {
    PluginResult result = new PluginResult(Status.ERROR, "Missing arguments");

    if (tracker == null) {
      result = new PluginResult(Status.ERROR, "Tracker not started. Call startTrackerWithId('UA-XXXXXX'); first.");

    } else {
      Product product = createProduct(options);
      Product relatedProduct = createRelatedProduct(options);
      ProductAction productAction = createProductAction(options);
      String productList = "";
      String productListSource = "";

      if (options.has(OPT_PRODUCT_LIST)) {
        productList = options.optString(OPT_PRODUCT_LIST);
      }

      if (options.has(OPT_PRODUCT_LIST_SOURCE)) {
        productListSource = options.optString(OPT_PRODUCT_LIST_SOURCE);
      }

      if (options.has(OPT_PRODUCT_SCREEN_NAME)) {
        tracker.setScreenName(options.optString(OPT_PRODUCT_SCREEN_NAME));
      }

      // THIS IS VERY UGLY AS HitBuilders.HitBuilder INTERFACE IS NOT VISIBLE
      if (options.has(OPT_CATEGORY) && options.has(OPT_ACTION)) {
        HitBuilders.EventBuilder builder = new HitBuilders.EventBuilder();
        prepareBuilder(builder, options);
        builder.setCategory(options.optString(OPT_CATEGORY));
        builder.setAction(options.optString(OPT_ACTION));

        if (options.has(OPT_LABEL)) {
          builder.setLabel(options.optString(OPT_LABEL));
        }
        if (options.has(OPT_VALUE)) {
          builder.setValue(options.optLong(OPT_VALUE));
        }

        /// <-- REPEATED CODE FOR SCREEN VIEWS (modifications here, copy paste below)
        if (productAction == null && product != null && productList.length() > 0) {
          builder.addImpression(product, productList);

        } else if (productAction != null) {
          builder.setProductAction(productAction);
          if (product != null) { // Could be a refund
            builder.addProduct(product);
          }

          if (relatedProduct != null && productList.length() > 0) {
            builder.addImpression(relatedProduct, productList);
          } else if (productList.length() > 0) {
            productAction.setProductActionList(productList);
          }

          if (productListSource.length() > 0) {
            productAction.setProductListSource(productListSource);
          }

        } else {
          builder = null;
        }

        if (builder != null) {
          tracker.send(builder.build());
          result = new PluginResult(Status.OK);
        }
        /// REPEATED CODE FOR SCREEN VIEWS -->

      } else {
        HitBuilders.ScreenViewBuilder builder = new HitBuilders.ScreenViewBuilder();
        prepareBuilder(builder, options);

        /// <-- REPEATED CODE FOR EVENTS (do not modify here, modify previous block)
        if (productAction == null && product != null && productList.length() > 0) {
          builder.addImpression(product, productList);

        } else if (productAction != null) {
          builder.setProductAction(productAction);
          if (product != null) { // Could be a refund
            builder.addProduct(product);
          }

          if (relatedProduct != null && productList.length() > 0) {
            builder.addImpression(relatedProduct, productList);
          } else if (productList.length() > 0) {
            productAction.setProductActionList(productList);
          }

          if (productListSource.length() > 0) {
            productAction.setProductListSource(productListSource);
          }

        } else {
          builder = null;
        }

        if (builder != null) {
          tracker.send(builder.build());
          result = new PluginResult(Status.OK);
        }
        /// REPEATED CODE FOR EVENTS -->
      }

      tracker.setScreenName(actualScreen);
    }

    return result;
  }

  private PluginResult execTrackPromotion(JSONObject options, CallbackContext callbackContext) {
    PluginResult result = new PluginResult(Status.ERROR, "Missing promotion id or promotion name");

    if (tracker == null) {
      result = new PluginResult(Status.ERROR, "Tracker not started. Call startTrackerWithId('UA-XXXXXX'); first.");

    } else if (options.has(OPT_PROMOTION_ID) || options.has(OPT_PROMOTION_NAME)) {
      Promotion promotion = new Promotion();
      ProductAction productAction = createProductAction(options);

      if (options.has(OPT_PROMOTION_ID)) {
        promotion.setId(options.optString(OPT_PROMOTION_ID));
      }

      if (options.has(OPT_PROMOTION_NAME)) {
        promotion.setName(options.optString(OPT_PROMOTION_NAME));
      }

      if (options.has(OPT_PROMOTION_CREATIVE)) {
        promotion.setCreative(options.optString(OPT_PROMOTION_CREATIVE));
      }

      if (options.has(OPT_PROMOTION_POSITION)) {
        promotion.setPosition(options.optString(OPT_PROMOTION_POSITION));
      }

      if (options.has(OPT_PRODUCT_SCREEN_NAME)) {
        tracker.setScreenName(options.optString(OPT_PRODUCT_SCREEN_NAME));
      }

      if (options.has(OPT_CATEGORY) && options.has(OPT_ACTION)) {
        HitBuilders.EventBuilder builder = new HitBuilders.EventBuilder();
        prepareBuilder(builder, options);
        builder.setCategory(options.optString(OPT_CATEGORY));
        builder.setAction(options.optString(OPT_ACTION));

        if (options.has(OPT_LABEL)) {
          builder.setLabel(options.optString(OPT_LABEL));
        }
        if (options.has(OPT_VALUE)) {
          builder.setValue(options.optLong(OPT_VALUE));
        }

        builder.addPromotion(promotion);
        if (productAction != null) {
          builder.setProductAction(productAction);
        }
        tracker.send(builder.build());

      } else {
        HitBuilders.ScreenViewBuilder builder = new HitBuilders.ScreenViewBuilder();
        prepareBuilder(builder, options);

        builder.addPromotion(promotion);
        if (productAction != null) {
          builder.setProductAction(productAction);
        }
        tracker.send(builder.build());
      }

      tracker.setScreenName(actualScreen);
      result = new PluginResult(Status.OK);
    }

    return result;
  }

  private PluginResult execTrackSocial(JSONObject options, CallbackContext callbackContext) {
    PluginResult result = new PluginResult(Status.ERROR, "Missing network, action or target");

    if (tracker == null) {
      result = new PluginResult(Status.ERROR, "Tracker not started. Call startTrackerWithId('UA-XXXXXX'); first.");

    } else if (options.has(OPT_NETWORK) && options.has(OPT_ACTION) && options.has(OPT_TARGET)) {
      HitBuilders.SocialBuilder builder = new HitBuilders.SocialBuilder();
      prepareBuilder(builder, options);

      builder.setNetwork(options.optString(OPT_NETWORK));
      builder.setAction(options.optString(OPT_ACTION));
      builder.setTarget(options.optString(OPT_TARGET));

      tracker.send(builder.build());
      result = new PluginResult(Status.OK);

    }

    return result;
  }

  private PluginResult execSetAppParams(JSONObject options, CallbackContext callbackContext) {
    if (options.has(OPT_APP_ID)) {
      tracker.setAppId(options.optString(OPT_APP_ID));
    }
    if (options.has(OPT_APP_INSTALLER_ID)) {
      tracker.setAppInstallerId(options.optString(OPT_APP_INSTALLER_ID));
    }
    if (options.has(OPT_APP_NAME)) {
      tracker.setAppName(options.optString(OPT_APP_NAME));
    }
    if (options.has(OPT_APP_VERSION)) {
      tracker.setAppVersion(options.optString(OPT_APP_VERSION));
    }

    return new PluginResult(Status.OK);
  }

  private PluginResult execSetCampaignFromUrl(String uriString, CallbackContext callbackContext) {
    PluginResult result = new PluginResult(Status.ERROR, "Missing url string");

    if (null != uriString && uriString.length() > 0) {
      this.campaignUrl = uriString;
      result = new PluginResult(Status.OK);
    }

    return result;
  }

  private PluginResult execSetCustomParams(JSONObject options, CallbackContext callbackContext) {
    int i;
    if (options != null) {
      JSONArray names = options.names();

      for (i = 0; i < names.length(); i += 1) {
        tracker.set(names.optString(i), options.optString(names.optString(i)));
      }
    }

    return new PluginResult(Status.OK);
  }

  private PluginResult execSetUserId(String userId, CallbackContext callbackContext) {
    PluginResult result = new PluginResult(Status.ERROR, "Missing user id");

    if (tracker == null) {
      result = new PluginResult(Status.ERROR, "Tracker not started. Call startTrackerWithId('UA-XXXXXX'); first.");

    } else if (null != userId && userId.length() > 0) {
      tracker.set("&uid", userId);
      result = new PluginResult(Status.OK);
    }

    return result;
  }

  private PluginResult execSetDebugMode(CallbackContext callbackContext) {
    GoogleAnalytics.getInstance(this.cordova.getActivity()).getLogger().setLogLevel(LogLevel.VERBOSE);
    return new PluginResult(Status.OK);
  }

  private Product createProduct(JSONObject options) {
    Product product = new Product();

    if (options.has(OPT_PRODUCT_ID) || options.has(OPT_PRODUCT_NAME)) {

      if (options.has(OPT_PRODUCT_ID)) {
        product.setId(options.optString(OPT_PRODUCT_ID));
      }

      if (options.has(OPT_PRODUCT_NAME)) {
        product.setName(options.optString(OPT_PRODUCT_NAME));
      }

      if (options.has(OPT_PRODUCT_CATEGORY)) {
        product.setCategory(options.optString(OPT_PRODUCT_CATEGORY));
      }

      if (options.has(OPT_PRODUCT_BRAND)) {
        product.setBrand(options.optString(OPT_PRODUCT_BRAND));
      }

      if (options.has(OPT_PRODUCT_VARIANT)) {
        product.setVariant(options.optString(OPT_PRODUCT_VARIANT));
      }

      if (options.has(OPT_PRODUCT_POSITION)) {
        product.setPosition(options.optInt(OPT_PRODUCT_POSITION));
      }

      if (options.has(OPT_PRODUCT_COUPON_CODE)) {
        product.setCouponCode(options.optString(OPT_PRODUCT_COUPON_CODE));
      }

      if (options.has(OPT_PRODUCT_QUANTITY)) {
        product.setQuantity(options.optInt(OPT_PRODUCT_QUANTITY));
      }

      if (options.has(OPT_PRODUCT_PRICE)) {
        product.setPrice(options.optDouble(OPT_PRODUCT_PRICE));
      }

    } else {
      product = null;
    }

    return product;
  }

  private Product createRelatedProduct(JSONObject options) {
    Product product = new Product();

    if (options.has(OPT_RELATED_PRODUCT_ID) || options.has(OPT_RELATED_PRODUCT_NAME)) {

      if (options.has(OPT_RELATED_PRODUCT_ID)) {
        product.setId(options.optString(OPT_RELATED_PRODUCT_ID));
      }

      if (options.has(OPT_RELATED_PRODUCT_NAME)) {
        product.setName(options.optString(OPT_RELATED_PRODUCT_NAME));
      }

      if (options.has(OPT_RELATED_PRODUCT_CATEGORY)) {
        product.setCategory(options.optString(OPT_RELATED_PRODUCT_CATEGORY));
      }

      if (options.has(OPT_RELATED_PRODUCT_BRAND)) {
        product.setBrand(options.optString(OPT_RELATED_PRODUCT_BRAND));
      }

      if (options.has(OPT_RELATED_PRODUCT_VARIANT)) {
        product.setVariant(options.optString(OPT_RELATED_PRODUCT_VARIANT));
      }

      if (options.has(OPT_RELATED_PRODUCT_POSITION)) {
        product.setPosition(options.optInt(OPT_RELATED_PRODUCT_POSITION));
      }

      if (options.has(OPT_RELATED_PRODUCT_COUPON_CODE)) {
        product.setCouponCode(options.optString(OPT_RELATED_PRODUCT_COUPON_CODE));
      }

      if (options.has(OPT_RELATED_PRODUCT_QUANTITY)) {
        product.setQuantity(options.optInt(OPT_RELATED_PRODUCT_QUANTITY));
      }

      if (options.has(OPT_RELATED_PRODUCT_PRICE)) {
        product.setPrice(options.optDouble(OPT_RELATED_PRODUCT_PRICE));
      }

    } else {
      product = null;
    }

    return product;
  }

  private ProductAction createProductAction(JSONObject options) {
    ProductAction productAction = null;
    String action = "";
    if (options.has(OPT_PRODUCT_ACTION)) {
      action = options.optString(OPT_PRODUCT_ACTION);
    }

    if (OPT_PRODUCT_ACTION_IMPRESSION.equals(action)) {
      productAction = null;

    } else if (OPT_PRODUCT_ACTION_ADD.equals(action)) {
      productAction = createProductAction(ProductAction.ACTION_ADD, options);

    } else if (OPT_PRODUCT_ACTION_CLICK.equals(action)) {
      productAction = createProductAction(ProductAction.ACTION_CLICK, options);

    } else if (OPT_PRODUCT_ACTION_DETAIL.equals(action)) {
      productAction = createProductAction(ProductAction.ACTION_DETAIL, options);

    } else if (OPT_PRODUCT_ACTION_PURCHASE.equals(action)) {
      productAction = createProductAction(ProductAction.ACTION_PURCHASE, options);

    } else if (OPT_PRODUCT_ACTION_REFUND.equals(action)) {
      productAction = createProductAction(ProductAction.ACTION_REFUND, options);

    } else if (OPT_PRODUCT_ACTION_CHECKOUT.equals(action)) {
      productAction = createProductAction(ProductAction.ACTION_CHECKOUT, options);

    } else if (OPT_PRODUCT_ACTION_CHECKOUT_OPTION.equals(action)) {
      productAction = createProductAction(ProductAction.ACTION_CHECKOUT_OPTION, options);

    } else if (OPT_PRODUCT_ACTION_REMOVE.equals(action)) {
      productAction = createProductAction(ProductAction.ACTION_REMOVE, options);

    }

    return productAction;
  }

  private ProductAction createProductAction(String action, JSONObject options) {
    ProductAction productAction = new ProductAction(action);

    if (options.has(OPT_TRANSACTION_ID)) {
      productAction.setTransactionId(options.optString(OPT_TRANSACTION_ID));
    }

    if (options.has(OPT_TRANSACTION_AFFILIATION)) {
      productAction.setTransactionAffiliation(options.optString(OPT_TRANSACTION_AFFILIATION));
    }

    if (options.has(OPT_TRANSACTION_REVENUE)) {
      productAction.setTransactionRevenue(options.optDouble(OPT_TRANSACTION_REVENUE));
    }

    if (options.has(OPT_TRANSACTION_TAX)) {
      productAction.setTransactionTax(options.optDouble(OPT_TRANSACTION_TAX));
    }

    if (options.has(OPT_TRANSACTION_SHIPPING)) {
      productAction.setTransactionShipping(options.optDouble(OPT_TRANSACTION_SHIPPING));
    }

    if (options.has(OPT_TRANSACTION_COUPON_CODE)) {
      productAction.setTransactionCouponCode(options.optString(OPT_TRANSACTION_COUPON_CODE));
    }

    if (options.has(OPT_CHECKOUT_STEP)) {
      productAction.setCheckoutStep(options.optInt(OPT_CHECKOUT_STEP));
    }

    if (options.has(OPT_CHECKOUT_OPTION)) {
      productAction.setCheckoutOptions(options.optString(OPT_CHECKOUT_OPTION));
    }

    return productAction;
  }

  private <T> void prepareBuilder(T builder, JSONObject options) {
    int i;
    Method builderMethod;
    String _campaignUrl = null;

    if (options.has(OPT_CUSTOM_DIMENSIONS)) {
      JSONObject dimensions = options.optJSONObject(OPT_CUSTOM_DIMENSIONS);
      JSONArray dimensionIndexs = dimensions.names();
      String dimensionIndex;
      String dimensionValue;

      try {
        builderMethod = builder.getClass().getMethod("setCustomDimension", int.class, String.class);

        for (i = 0; i < dimensionIndexs.length(); i += 1) {
          try {
            dimensionIndex = dimensionIndexs.optString(i);
            dimensionValue = dimensions.optString(dimensionIndex);
            builderMethod.invoke(builder, Integer.parseInt(dimensionIndex), dimensionValue);
          } catch (IllegalArgumentException e) {
          } catch (IllegalAccessException e) {
          } catch (InvocationTargetException e) {
          }
        }
      } catch (SecurityException e) {
      } catch (NoSuchMethodException e) {
      }
    }

    if (options.has(OPT_CUSTOM_METRICS)) {
      JSONObject metrics = options.optJSONObject(OPT_CUSTOM_METRICS);
      JSONArray metricsIndexs = metrics.names();
      String metricIndex;
      float metricValue;

      try {
        builderMethod = builder.getClass().getMethod("setCustomMetric", int.class, float.class);

        for (i = 0; i < metricsIndexs.length(); i += 1) {
          try {
            metricIndex = metricsIndexs.optString(i);
            metricValue = (float) metrics.optDouble(metricIndex, 0);
            builderMethod.invoke(builder, Integer.parseInt(metricIndex), metricValue);
          } catch (IllegalArgumentException e) {
          } catch (IllegalAccessException e) {
          } catch (InvocationTargetException e) {
          }
        }
      } catch (SecurityException e) {
      } catch (NoSuchMethodException e) {
      }
    }

    if (options.has(OPT_CURRENCY_CODE)) {
      tracker.set("&cu", options.optString(OPT_CAMPAIGN_URL));
    }

    if (options.has(OPT_CAMPAIGN_URL)) {
      _campaignUrl = options.optString(OPT_CAMPAIGN_URL);
    } else if (this.campaignUrl != null && !"".equals(this.campaignUrl)) {
      _campaignUrl = this.campaignUrl;
    }

    if (_campaignUrl != null && !"".equals(_campaignUrl)) {
      try {
        builderMethod = builder.getClass().getMethod("setCampaignParamsFromUrl", String.class);
        try {
          builderMethod.invoke(builder, this.campaignUrl);
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }

      } catch (SecurityException e) {
      } catch (NoSuchMethodException e) {
      }
    }
  }

  /*private void prepareBuilder(HitBuilders.TimingBuilder builder, JSONObject options) {
    int i;

    if (options.has(OPT_CUSTOM_DIMENSIONS)) {
      JSONArray dimensions = options.optJSONArray(OPT_CUSTOM_DIMENSIONS);

      for (i = 0; i < dimensions.length(); i += 1) {
        builder.setCustomDimension(i, dimensions.optString(i));
      }
    }

    if (options.has(OPT_CUSTOM_METRICS)) {
      JSONArray dimensions = options.optJSONArray(OPT_CUSTOM_METRICS);

      for (i = 0; i < dimensions.length(); i += 1) {
        builder.setCustomMetric(i, dimensions.optInt(i));
      }
    }

    if (options.has(OPT_CAMPAIGN_URL)) {
      this.campaignUrl = options.optString(OPT_CAMPAIGN_URL);
    }

    if (this.campaignUrl != null && !"".equals(this.campaignUrl)) {
      builder.setCampaignParamsFromUrl(this.campaignUrl);
    }
  }*/

}
