/*
 CDVGoogleAnalytics.m
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


#import "CDVGoogleAnalytics.h"

@interface CDVGoogleAnalytics()

- (GAIDictionaryBuilder *) prepareBuilder: (GAIDictionaryBuilder *)builder withOptions: (NSDictionary *)options;
- (GAIEcommerceProduct *) createProduct: (NSDictionary *)options;
- (GAIEcommerceProduct *) createRelatedProduct: (NSDictionary *)options;
- (GAIEcommerceProductAction *) createProductAction: (NSDictionary *)options;
- (GAIEcommerceProductAction *) createProductAction: (NSString *)action withOptions:(NSDictionary *)options;


@end

@implementation CDVGoogleAnalytics

static id<GAITracker> _tracker = nil;

- (void) pluginInitialize {
    _actualScreen = @"";
    _campaignUrl = nil;
}

+ (id<GAITracker>) getTracker {
    return _tracker;
}

+ (void) setTracker: (id<GAITracker>)tracker {
    _tracker = tracker;
}

#pragma mark -
#pragma mark Cordova JS Bridge

- (void) startTrackerWithId: (CDVInvokedUrlCommand *)command {
    CDVPluginResult *pluginResult = nil;
    NSArray *args = command.arguments;
    
    if ([args count] > 0 && [[command.arguments objectAtIndex:0] length] > 0) {
        NSString *accountId = [command.arguments objectAtIndex:0];
        [GAI sharedInstance].dispatchInterval = 30;
        [[GAI sharedInstance].logger setLogLevel:kGAILogLevelError];
        _tracker = [[GAI sharedInstance] trackerWithTrackingId:accountId];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Invalid tracker"];
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void) setDispatchPeriod: (CDVInvokedUrlCommand *)command {
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Missing dispatch period value"];
    NSArray *args = command.arguments;
    
    if ([args count] > 0 && [[command.arguments objectAtIndex:0] length] > 0) {
        NSString *dispatchPeriod = [command.arguments objectAtIndex:0];
        if (dispatchPeriod && dispatchPeriod.length > 0) {
            [GAI sharedInstance].dispatchInterval = [dispatchPeriod intValue];
        }
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void) trackView: (CDVInvokedUrlCommand *)command {
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Missing screen name"];
    NSArray *args = command.arguments;
    
    if ( ! _tracker) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Tracker not started. Call startTrackerWithId('UA-XXXXXX'); first."];

    } else if ([args count] > 0) {
        NSDictionary *options = [command argumentAtIndex:0 withDefault:[NSNull null]];
        
        if (options) {
            NSString *screenName = [options objectForKey:CDVGA_OPT_SCREEN_NAME];
            if (screenName && screenName.length > 0) {
                GAIDictionaryBuilder *builder = [self prepareBuilder:[GAIDictionaryBuilder createAppView] withOptions:options];
                _actualScreen = screenName;
                [_tracker set:kGAIScreenName value:screenName];
                [_tracker send:[builder build]];
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
            }
        }
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void) trackEvent: (CDVInvokedUrlCommand *)command {
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Missing category or action"];
    NSArray *args = command.arguments;
    
    if ( ! _tracker) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Tracker not started. Call startTrackerWithId('UA-XXXXXX'); first."];
        
    } else if ([args count] > 0) {
        NSDictionary *options = [command argumentAtIndex:0 withDefault:[NSNull null]];
        
        if (options) {
            NSString *category = [options objectForKey:CDVGA_OPT_CATEGORY];
            NSString *action = [options objectForKey:CDVGA_OPT_ACTION];
            NSString *label = [options objectForKey:CDVGA_OPT_LABEL];
            NSString *value = [options objectForKey:CDVGA_OPT_VALUE];
            
            if (category && category.length > 0 && action && action.length > 0) {
                GAIDictionaryBuilder *builder = [GAIDictionaryBuilder createEventWithCategory:category
                                                                                       action:action
                                                                                        label:label
                                                                                        value:[NSNumber numberWithInt:[value intValue]]];
                
                builder = [self prepareBuilder:builder withOptions:options];
                [_tracker send:[builder build]];
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
            }
        }
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void) trackTiming: (CDVInvokedUrlCommand *)command {
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Missing category, variable to measure or value"];
    NSArray *args = command.arguments;
    
    if ( ! _tracker) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Tracker not started. Call startTrackerWithId('UA-XXXXXX'); first."];
        
    } else if ([args count] > 0) {
        NSDictionary *options = [command argumentAtIndex:0 withDefault:[NSNull null]];
        
        if (options) {
            NSString *category = [options objectForKey:CDVGA_OPT_CATEGORY];
            NSString *value = [options objectForKey:CDVGA_OPT_VALUE];
            NSString *variable = [options objectForKey:CDVGA_OPT_VARIABLE];
            NSString *label = [options objectForKey:CDVGA_OPT_LABEL];
            
            if (category && category.length > 0 && variable && variable.length > 0 && value != nil) {
                GAIDictionaryBuilder *builder = [GAIDictionaryBuilder createTimingWithCategory:category
                                                                                      interval:[NSNumber numberWithInt:[value intValue]]
                                                                                          name:variable
                                                                                         label:label];
                builder = [self prepareBuilder:builder withOptions:options];
                [_tracker send:[builder build]];
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
            }
        }
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void) trackEcommerce: (CDVInvokedUrlCommand *)command {
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Missing arguments"];
    NSArray *args = command.arguments;
    
    if ( ! _tracker) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Tracker not started. Call startTrackerWithId('UA-XXXXXX'); first."];
        
    } else if ([args count] > 0) {
        NSDictionary *options = [command argumentAtIndex:0 withDefault:[NSNull null]];
        
        if (options) {
            GAIDictionaryBuilder *builder;
            GAIEcommerceProduct *product = [self createProduct:options];
            GAIEcommerceProduct *relatedProduct = [self createRelatedProduct:options];
            GAIEcommerceProductAction *productAction = [self createProductAction:options];
            NSString *productList = [options objectForKey:CDVGA_OPT_PRODUCT_LIST];
            NSString *productListSource = [options objectForKey:CDVGA_OPT_PRODUCT_LIST_SOURCE];
            NSString *productScreen = [options objectForKey:CDVGA_OPT_PRODUCT_SCREEN_NAME];
            NSString *category = [options objectForKey:CDVGA_OPT_CATEGORY];
            NSString *action = [options objectForKey:CDVGA_OPT_ACTION];
            NSString *label = [options objectForKey:CDVGA_OPT_LABEL];
            NSString *value = [options objectForKey:CDVGA_OPT_VALUE];
            
            if (category && category.length > 0 && action && action.length > 0) {
                builder = [GAIDictionaryBuilder createEventWithCategory:category
                                                                 action:action
                                                                  label:label
                                                                  value:[NSNumber numberWithInt:[value intValue]]];
                
            } else {
                builder = [GAIDictionaryBuilder createScreenView];
            }
            
            builder = [self prepareBuilder:builder withOptions:options];
            
            if ( ! productListSource) {
                productListSource = @"";
            }
            
            if (productScreen && productScreen.length > 0) {
                [_tracker set:kGAIScreenName value:productScreen];
            }
            
            if (productAction == NULL && product != NULL && productList && productList.length > 0) {
                [builder addProductImpression:product impressionList:productList impressionSource:productListSource];
                
            } else if (productAction != NULL) {
                [builder setProductAction:productAction];
                if (product != NULL) { // Could be a refund
                    [builder addProduct:product];
                }
                
                if (relatedProduct != NULL && productList && productList.length > 0) {
                    [builder addProductImpression:relatedProduct impressionList:productList impressionSource:productListSource];
                } else if (productList && productList.length > 0) {
                    [productAction setProductActionList:productList];
                }
                
            } else {
                builder = NULL;
            }
            
            if (builder != NULL) {
                [_tracker send:[builder build]];
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
            }
            
            [_tracker set:kGAIScreenName value:_actualScreen];
            
        }
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void) trackPromotion: (CDVInvokedUrlCommand *)command {
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Missing promotion id or promotion name"];
    NSArray *args = command.arguments;
    
    if ( ! _tracker) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Tracker not started. Call startTrackerWithId('UA-XXXXXX'); first."];
        
    } else if ([args count] > 0) {
        NSDictionary *options = [command argumentAtIndex:0 withDefault:[NSNull null]];
        
        if (options) {
            NSString *promotionId = [options objectForKey:CDVGA_OPT_PROMOTION_ID];
            NSString *promotionName = [options objectForKey:CDVGA_OPT_PROMOTION_NAME];
            NSString *promotionCreative = [options objectForKey:CDVGA_OPT_PROMOTION_CREATIVE];
            NSString *promotionPosition = [options objectForKey:CDVGA_OPT_PROMOTION_POSITION];
            NSString *productScreen = [options objectForKey:CDVGA_OPT_PRODUCT_SCREEN_NAME];
            NSString *category = [options objectForKey:CDVGA_OPT_CATEGORY];
            NSString *action = [options objectForKey:CDVGA_OPT_ACTION];
            NSString *label = [options objectForKey:CDVGA_OPT_LABEL];
            NSString *value = [options objectForKey:CDVGA_OPT_VALUE];
            
            if ( (promotionId && promotionId.length > 0) || (promotionName && promotionName.length > 0) ) {
                GAIEcommercePromotion *promotion = [[GAIEcommercePromotion alloc] init];
                GAIDictionaryBuilder *builder;
                GAIEcommerceProductAction *productAction = [self createProductAction:options];
                
                if (promotionId && promotionId.length > 0) {
                    [promotion setId:promotionId];
                }
                if (promotionName && promotionName.length > 0) {
                    [promotion setName:promotionName];
                }
                if (promotionCreative && promotionCreative.length > 0) {
                    [promotion setCreative:promotionCreative];
                }
                if (promotionPosition && promotionPosition.length > 0) {
                    [promotion setPosition:promotionPosition];
                }
                
                if (productScreen && productScreen.length > 0) {
                    [_tracker set:kGAIScreenName value:productScreen];
                }
                
                if (category && category.length > 0 && action && action.length > 0) {
                    builder = [GAIDictionaryBuilder createEventWithCategory:category
                                                                     action:action
                                                                      label:label
                                                                      value:[NSNumber numberWithInt:[value intValue]]];
                    
                } else {
                    builder = [GAIDictionaryBuilder createScreenView];
                }
                
                builder = [self prepareBuilder:builder withOptions:options];
                if (productAction != NULL) {
                    [builder setProductAction:productAction];
                }
                
                [builder addPromotion:promotion];
                [_tracker send:[builder build]];
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
                
                [_tracker set:kGAIScreenName value:_actualScreen];
            }
        }
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void) trackSocial: (CDVInvokedUrlCommand *)command {
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Missing network, action or target"];
    NSArray *args = command.arguments;
    
    if ( ! _tracker) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Tracker not started. Call startTrackerWithId('UA-XXXXXX'); first."];
        
    } else if ([args count] > 0) {
        NSDictionary *options = [command argumentAtIndex:0 withDefault:[NSNull null]];
        
        if (options) {
            NSString *network = [options objectForKey:CDVGA_OPT_NETWORK];
            NSString *action = [options objectForKey:CDVGA_OPT_ACTION];
            NSString *target = [options objectForKey:CDVGA_OPT_TARGET];
            
            if (network && network.length > 0 && action && action.length > 0 && target && target.length > 0) {
                GAIDictionaryBuilder *builder = [GAIDictionaryBuilder  createSocialWithNetwork:network
                                                                                        action:action
                                                                                        target:target];
                builder = [self prepareBuilder:builder withOptions:options];
                [_tracker send:[builder build]];
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
            }
        }
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void) setAppParams: (CDVInvokedUrlCommand *)command {
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    NSArray *args = command.arguments;
    
    if ([args count] > 0) {
        NSDictionary *options = [command argumentAtIndex:0 withDefault:[NSNull null]];
        
        if (options) {
            NSString *appId = [options objectForKey:CDVGA_OPT_APP_ID];
            NSString *appInstallerId = [options objectForKey:CDVGA_OPT_APP_INSTALLER_ID];
            NSString *appName = [options objectForKey:CDVGA_OPT_APP_NAME];
            NSString *appVersion = [options objectForKey:CDVGA_OPT_APP_VERSION];
            
            if (appId && appId.length > 0) {
                [_tracker set:kGAIAppId value:appId];
            }
            if (appInstallerId && appInstallerId.length > 0) {
                [_tracker set:kGAIAppInstallerId value:appInstallerId];
            }
            if (appName && appName.length > 0) {
                [_tracker set:kGAIAppName value:appName];
            }
            if (appVersion && appVersion.length > 0) {
                [_tracker set:kGAIAppVersion value:appVersion];
            }
            
        }
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void) setCampaignFromUrl: (CDVInvokedUrlCommand*)command {
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Missing url string"];

    if ([command.arguments count] > 0) {
        _campaignUrl = [command.arguments objectAtIndex:0];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void) setCustomParams: (CDVInvokedUrlCommand*)command {
    if ([command.arguments count] > 0) {
        NSDictionary *options = [command argumentAtIndex:0 withDefault:[NSNull null]];
        NSArray *names = [options allKeys];
        NSString *name;
        int i;
        
        for (i = 0; i < [names count]; i += 1) {
            name = [names objectAtIndex:i];
            [_tracker set:name value:[options objectForKey:name]];
        }
    }
    
    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
}

- (void) setUserId: (CDVInvokedUrlCommand*)command {
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Missing user id"];
    NSString* userId = [command.arguments objectAtIndex:0];
    
    if ( ! _tracker) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Tracker not started. Call startTrackerWithId('UA-XXXXXX'); first."];
        
    } else if (userId && userId.length > 0) {
        [_tracker set:@"&uid" value: userId];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void) setDebugMode: (CDVInvokedUrlCommand*) command {
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [[GAI sharedInstance].logger setLogLevel:kGAILogLevelVerbose];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

#pragma mark -
#pragma mark Internal functions

- (GAIDictionaryBuilder*) prepareBuilder: (GAIDictionaryBuilder*)builder withOptions:(NSDictionary*)options{
    NSString *campaignUrl = nil;
    
    if (options) {
        if ([options valueForKey:CDVGA_OPT_CUSTOM_DIMENSIONS]) {
            NSDictionary *dimensions = [options valueForKey:CDVGA_OPT_CUSTOM_DIMENSIONS];
            NSArray *dimensionIndexs = [dimensions allKeys];
            NSString *dimensionIndex;
            NSString *dimensionValue;
            int i;
            
            for (i = 0; i < dimensionIndexs.count; i += 1) {
                dimensionIndex = [dimensionIndexs objectAtIndex:i];
                dimensionValue = [dimensions valueForKey:dimensionIndex];
                
                [_tracker set:[GAIFields customDimensionForIndex:[dimensionIndex integerValue]]
                        value:dimensionValue];
                
                [builder set:dimensionValue
                      forKey:[GAIFields customDimensionForIndex:[dimensionIndex integerValue]]];
            }
        }
        
        if ([options valueForKey:CDVGA_OPT_CUSTOM_METRICS]) {
            NSDictionary *metrics = [options valueForKey:CDVGA_OPT_CUSTOM_METRICS];
            NSArray *metricsIndexs = [metrics allKeys];
            NSString *metricIndex;
            NSString *metricValue;
            int i;
            
            for (i = 0; i < metricsIndexs.count; i += 1) {
                metricIndex = [metricsIndexs objectAtIndex:i];
                metricValue = [metrics valueForKey:metricIndex];
                [builder set:metricValue
                      forKey:[GAIFields customMetricForIndex:[metricIndex integerValue]]];
            }
        }
        
        if ([options valueForKey:CDVGA_OPT_CAMPAIGN_URL]) {
            campaignUrl = [options valueForKey:CDVGA_OPT_CAMPAIGN_URL];
        }

        if ([options valueForKey:CDVGA_OPT_CURRENCY_CODE]) {
            [_tracker set:kGAICurrencyCode value:[options valueForKey:CDVGA_OPT_CURRENCY_CODE]];
        }
    }
    
    if (campaignUrl && campaignUrl.length > 0) {
        [builder setCampaignParametersFromUrl:campaignUrl];
    } else if (_campaignUrl && _campaignUrl.length > 0) {
        [builder setCampaignParametersFromUrl:_campaignUrl];
    }
    
    return builder;
}

- (GAIEcommerceProduct *) createProduct: (NSDictionary *)options {
    GAIEcommerceProduct *product = [[GAIEcommerceProduct alloc] init];
    NSString *productId = [options objectForKey:CDVGA_OPT_PRODUCT_ID];
    NSString *productName = [options objectForKey:CDVGA_OPT_PRODUCT_NAME];
    NSString *productCategory = [options objectForKey:CDVGA_OPT_PRODUCT_CATEGORY];
    NSString *productBrand = [options objectForKey:CDVGA_OPT_PRODUCT_BRAND];
    NSString *productVariant = [options objectForKey:CDVGA_OPT_PRODUCT_VARIANT];
    NSString *productPosition = [options objectForKey:CDVGA_OPT_PRODUCT_POSITION];
    NSString *productCouponCode = [options objectForKey:CDVGA_OPT_PRODUCT_COUPON_CODE];
    NSString *productQuantity = [options objectForKey:CDVGA_OPT_PRODUCT_QUANTITY];
    NSString *productPrice = [options objectForKey:CDVGA_OPT_PRODUCT_PRICE];
    
    if ( (productId && productId.length > 0) || (productName && productName.length > 0) ) {
        if (productId && productId.length > 0) {
            [product setId:productId];
        }
        if (productName && productName.length > 0) {
            [product setName:productName];
        }
        if (productCategory && productCategory.length > 0) {
            [product setCategory:productCategory];
        }
        if (productBrand && productBrand.length > 0) {
            [product setBrand:productBrand];
        }
        if (productVariant && productVariant.length > 0) {
            [product setVariant:productVariant];
        }
        if (productPosition) {
            [product setPosition:[NSNumber numberWithInt:[productPosition intValue]]];
        }
        if (productCouponCode && productCouponCode.length > 0) {
            [product setCouponCode:productCouponCode];
        }
        if (productQuantity) {
            [product setQuantity:[NSNumber numberWithInt:[productQuantity intValue]]];
        }
        if (productPrice) {
            [product setPrice:[NSNumber numberWithDouble:[productPrice doubleValue]]];
        }
    } else {
        product = NULL;
    }
    
    return product;
    
}

- (GAIEcommerceProduct *) createRelatedProduct: (NSDictionary *)options {
    GAIEcommerceProduct *product = [[GAIEcommerceProduct alloc] init];
    NSString *productId = [options objectForKey:CDVGA_OPT_RELATED_PRODUCT_ID];
    NSString *productName = [options objectForKey:CDVGA_OPT_RELATED_PRODUCT_NAME];
    NSString *productCategory = [options objectForKey:CDVGA_OPT_RELATED_PRODUCT_CATEGORY];
    NSString *productBrand = [options objectForKey:CDVGA_OPT_RELATED_PRODUCT_BRAND];
    NSString *productVariant = [options objectForKey:CDVGA_OPT_RELATED_PRODUCT_VARIANT];
    NSString *productPosition = [options objectForKey:CDVGA_OPT_RELATED_PRODUCT_POSITION];
    NSString *productCouponCode = [options objectForKey:CDVGA_OPT_RELATED_PRODUCT_COUPON_CODE];
    NSString *productQuantity = [options objectForKey:CDVGA_OPT_RELATED_PRODUCT_QUANTITY];
    NSString *productPrice = [options objectForKey:CDVGA_OPT_RELATED_PRODUCT_PRICE];

    if ( (productId && productId.length > 0) || (productName && productName.length > 0) ) {
        if (productId && productId.length > 0) {
            [product setId:productId];
        }
        if (productName && productName.length > 0) {
            [product setName:productName];
        }
        if (productCategory && productCategory.length > 0) {
            [product setCategory:productCategory];
        }
        if (productBrand && productBrand.length > 0) {
            [product setBrand:productBrand];
        }
        if (productVariant && productVariant.length > 0) {
            [product setVariant:productVariant];
        }
        if (productPosition) {
            [product setPosition:[NSNumber numberWithInt:[productPosition intValue]]];
        }
        if (productCouponCode && productCouponCode.length > 0) {
            [product setCouponCode:productCouponCode];
        }
        if (productQuantity) {
            [product setQuantity:[NSNumber numberWithInt:[productQuantity intValue]]];
        }
        if (productPrice) {
            [product setPrice:[NSNumber numberWithDouble:[productPrice doubleValue]]];
        }
    } else {
        product = NULL;
    }
    
    return product;
}

- (GAIEcommerceProductAction *) createProductAction: (NSDictionary *)options {
    GAIEcommerceProductAction *productAction = NULL;
    NSString *action = [options objectForKey:CDVGA_OPT_PRODUCT_ACTION];
    
    if ([CDVGA_OPT_PRODUCT_ACTION_IMPRESSION isEqualToString:action]) {
        productAction = NULL;
        
    } else if ([CDVGA_OPT_PRODUCT_ACTION_ADD isEqualToString:action]) {
        productAction = [self createProductAction:kGAIPAAdd withOptions:options];
        
    } else if ([CDVGA_OPT_PRODUCT_ACTION_CLICK isEqualToString:action]) {
        productAction = [self createProductAction:kGAIPAClick withOptions:options];
        
    } else if ([CDVGA_OPT_PRODUCT_ACTION_DETAIL isEqualToString:action]) {
        productAction = [self createProductAction:kGAIPADetail withOptions:options];
        
    } else if ([CDVGA_OPT_PRODUCT_ACTION_PURCHASE isEqualToString:action]) {
        productAction = [self createProductAction:kGAIPAPurchase withOptions:options];
        
    } else if ([CDVGA_OPT_PRODUCT_ACTION_REFUND isEqualToString:action]) {
        productAction = [self createProductAction:kGAIPARefund withOptions:options];
        
    } else if ([CDVGA_OPT_PRODUCT_ACTION_CHECKOUT isEqualToString:action]) {
        productAction = [self createProductAction:kGAIPACheckout withOptions:options];
        
    } else if ([CDVGA_OPT_PRODUCT_ACTION_CHECKOUT_OPTION isEqualToString:action]) {
        productAction = [self createProductAction:kGAIPACheckoutOption withOptions:options];
        
    } else if ([CDVGA_OPT_PRODUCT_ACTION_REMOVE isEqualToString:action]) {
        productAction = [self createProductAction:kGAIPARemove withOptions:options];
        
    }
    
    return productAction;
}

- (GAIEcommerceProductAction *) createProductAction: (NSString *)action withOptions:(NSDictionary *)options {
    GAIEcommerceProductAction *productAtion = [[GAIEcommerceProductAction alloc] init];
    
    NSString *transactionId = [options objectForKey:CDVGA_OPT_TRANSACTION_ID];
    NSString *trasactionAffiliation = [options objectForKey:CDVGA_OPT_TRANSACTION_AFFILIATION];
    NSString *transactionRevenue = [options objectForKey:CDVGA_OPT_TRANSACTION_REVENUE];
    NSString *transactionTax = [options objectForKey:CDVGA_OPT_TRANSACTION_TAX];
    NSString *transactionShipping = [options objectForKey:CDVGA_OPT_TRANSACTION_SHIPPING];
    NSString *transactionCouponCode = [options objectForKey:CDVGA_OPT_TRANSACTION_COUPON_CODE];
    NSString *checkoutStep = [options objectForKey:CDVGA_OPT_CHECKOUT_STEP];
    NSString *checkoutOption = [options objectForKey:CDVGA_OPT_CHECKOUT_OPTION];
    
    if (transactionId && transactionId.length > 0) {
        [productAtion setTransactionId:transactionId];
    }
    if (trasactionAffiliation && trasactionAffiliation.length > 0) {
        [productAtion setAffiliation:trasactionAffiliation];
    }
    if (transactionRevenue) {
        [productAtion setRevenue:[NSNumber numberWithDouble:[transactionRevenue doubleValue]]];
    }
    if (transactionTax) {
        [productAtion setTax:[NSNumber numberWithDouble:[transactionTax doubleValue]]];
    }
    if (transactionShipping) {
        [productAtion setShipping:[NSNumber numberWithDouble:[transactionShipping doubleValue]]];
    }
    if (transactionCouponCode && transactionCouponCode.length > 0) {
        [productAtion setCouponCode:transactionCouponCode];
    }
    if (checkoutStep) {
        [productAtion setCheckoutStep:[NSNumber numberWithInt:[checkoutStep intValue]]];
    }
    if (checkoutOption && checkoutOption.length > 0) {
        [productAtion setCheckoutOption:checkoutOption];
    }
    
    return productAtion;
}

@end
