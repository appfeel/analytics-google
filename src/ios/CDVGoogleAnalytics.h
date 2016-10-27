/*
 CDVGoogleAnalytics.h
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

#import <Foundation/Foundation.h>
#import <Cordova/CDV.h>
#import "GAI.h"
#import "GAIDictionaryBuilder.h"
#import "GAIFields.h"
#import "GAIEcommerceFields.h"

#define CDVGA_OPT_SCREEN_NAME                       @"screenName"
#define CDVGA_OPT_CATEGORY                          @"category"
#define CDVGA_OPT_ACTION                            @"action"
#define CDVGA_OPT_LABEL                             @"label"
#define CDVGA_OPT_VALUE                             @"value"
#define CDVGA_OPT_VARIABLE                          @"variable"
#define CDVGA_OPT_NETWORK                           @"network"
#define CDVGA_OPT_TARGET                            @"target"
#define CDVGA_OPT_CUSTOM_DIMENSIONS                 @"customDimensions"
#define CDVGA_OPT_CUSTOM_METRICS                    @"customMetrics"
#define CDVGA_OPT_CAMPAIGN_URL                      @"campaignUrl"

#define CDVGA_OPT_APP_ID                            @"appId"
#define CDVGA_OPT_APP_INSTALLER_ID                  @"appInstallerId"
#define CDVGA_OPT_APP_NAME                          @"appName"
#define CDVGA_OPT_APP_VERSION                       @"appVersion"

#define CDVGA_OPT_CURRENCY_CODE                     @"currencyCode"
#define CDVGA_OPT_PRODUCT_ID                        @"productId"
#define CDVGA_OPT_PRODUCT_NAME                      @"productName"
#define CDVGA_OPT_PRODUCT_CATEGORY                  @"productCategory"
#define CDVGA_OPT_PRODUCT_BRAND                     @"productBrand"
#define CDVGA_OPT_PRODUCT_VARIANT                   @"productVariant"
#define CDVGA_OPT_PRODUCT_POSITION                  @"productPosition"
#define CDVGA_OPT_PRODUCT_SCREEN_NAME               @"productScreenName"
#define CDVGA_OPT_PRODUCT_COUPON_CODE               @"productCouponCode"
#define CDVGA_OPT_PRODUCT_QUANTITY                  @"productQuantity"
#define CDVGA_OPT_PRODUCT_PRICE                     @"productPrice"

#define CDVGA_OPT_RELATED_PRODUCT_ID                @"relatedProductId"
#define CDVGA_OPT_RELATED_PRODUCT_NAME              @"relatedProductName"
#define CDVGA_OPT_RELATED_PRODUCT_CATEGORY          @"relatedProductCategory"
#define CDVGA_OPT_RELATED_PRODUCT_BRAND             @"relatedProductBrand"
#define CDVGA_OPT_RELATED_PRODUCT_VARIANT           @"relatedProductVariant"
#define CDVGA_OPT_RELATED_PRODUCT_POSITION          @"relatedProductPosition"
#define CDVGA_OPT_RELATED_PRODUCT_COUPON_CODE       @"relatedProductCouponCode"
#define CDVGA_OPT_RELATED_PRODUCT_QUANTITY          @"relatedProductQuantity"
#define CDVGA_OPT_RELATED_PRODUCT_PRICE             @"relatedProductPrice"

#define CDVGA_OPT_PRODUCT_LIST                      @"productList"
#define CDVGA_OPT_PRODUCT_LIST_SOURCE               @"productListSource"

#define CDVGA_OPT_PRODUCT_ACTION                    @"productAction"
#define CDVGA_OPT_PRODUCT_ACTION_IMPRESSION         @"productActionImpression"
#define CDVGA_OPT_PRODUCT_ACTION_DETAIL             @"productActionDetail"
#define CDVGA_OPT_PRODUCT_ACTION_PURCHASE           @"productActionPurchase"
#define CDVGA_OPT_PRODUCT_ACTION_REFUND             @"productActionRefund"
#define CDVGA_OPT_PRODUCT_ACTION_CHECKOUT           @"productActionCheckout"
#define CDVGA_OPT_PRODUCT_ACTION_CHECKOUT_OPTION    @"productActionCheckoutOption"
#define CDVGA_OPT_PRODUCT_ACTION_CLICK              @"productActionClick"
#define CDVGA_OPT_PRODUCT_ACTION_ADD                @"productActionAdd"
#define CDVGA_OPT_PRODUCT_ACTION_REMOVE             @"productActionRemove"

#define CDVGA_OPT_TRANSACTION_ID                    @"transactionId"
#define CDVGA_OPT_TRANSACTION_AFFILIATION           @"transactionAffiliation"
#define CDVGA_OPT_TRANSACTION_REVENUE               @"transactionRevenue"
#define CDVGA_OPT_TRANSACTION_TAX                   @"transactionTax"
#define CDVGA_OPT_TRANSACTION_SHIPPING              @"transactionShipping"
#define CDVGA_OPT_TRANSACTION_COUPON_CODE           @"transactionCouponCode"

#define CDVGA_OPT_CHECKOUT_STEP                     @"checkoutStep"
#define CDVGA_OPT_CHECKOUT_OPTION                   @"checkoutOption"

#define CDVGA_OPT_PROMOTION_ID                      @"promotionId"
#define CDVGA_OPT_PROMOTION_NAME                    @"promotionName"
#define CDVGA_OPT_PROMOTION_CREATIVE                @"promotionCreative"
#define CDVGA_OPT_PROMOTION_POSITION                @"promotionPosition"


@interface CDVGoogleAnalytics : CDVPlugin {
    NSString *_campaignUrl;
    NSString *_actualScreen;
}

+ (id<GAITracker>) getTracker;
+ (void) setTracker: (id<GAITracker>)tracker;

- (void) startTrackerWithId: (CDVInvokedUrlCommand*)command;
- (void) trackView: (CDVInvokedUrlCommand*)command;
- (void) trackEvent: (CDVInvokedUrlCommand*)command;
- (void) trackTiming: (CDVInvokedUrlCommand*)command;
- (void) trackEcommerce: (CDVInvokedUrlCommand*)command;
- (void) trackPromotion: (CDVInvokedUrlCommand*)command;
- (void) trackSocial: (CDVInvokedUrlCommand*)command;
- (void) setDispatchPeriod: (CDVInvokedUrlCommand *)command;
- (void) setAppParams: (CDVInvokedUrlCommand*)command;
- (void) setCampaignFromUrl: (CDVInvokedUrlCommand*)command;
- (void) setCustomParams: (CDVInvokedUrlCommand*)command;
- (void) setUserId: (CDVInvokedUrlCommand*)command;
- (void) setDebugMode: (CDVInvokedUrlCommand*)command;

@end

