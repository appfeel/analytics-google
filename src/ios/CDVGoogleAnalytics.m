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
@import FirebaseAnalytics;

@implementation CDVGoogleAnalytics

- (void) pluginInitialize {
    _currentScreen = @"";
    if ([FIRApp defaultApp] == nil) {
        [FIRApp configure];
    }
}

#pragma mark -
#pragma mark Cordova JS Bridge

- (void) logEvent: (CDVInvokedUrlCommand *)command {
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Missing event name"];
    
    if ([command.arguments count] > 0) {
        NSDictionary *options = [command argumentAtIndex:0 withDefault:[NSNull null]];
        if (options) {
            NSString *eventName = [options objectForKey:CDVGA_OPT_EVENT_NAME];
            if (eventName && eventName.length > 0) {
                NSDictionary *parameters = [options objectForKey:CDVGA_OPT_EVENT_PARAMS];
                [FIRAnalytics logEventWithName:eventName parameters:parameters];
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
            }
        }
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void) resetAnalyticsData: (CDVInvokedUrlCommand*) command {
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [FIRAnalytics resetAnalyticsData];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void) setAnalyticsCollectionEnabled: (CDVInvokedUrlCommand*) command {
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void) setCurrentScreen: (CDVInvokedUrlCommand*)command {
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Missing current screen name"];
    
    if ([command.arguments count] > 0) {
        NSString* currentScreen = [command.arguments objectAtIndex:0];
        if (currentScreen && currentScreen.length > 0) {
            _currentScreen = currentScreen;
            [FIRAnalytics setScreenName:_currentScreen screenClass:nil];
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        }
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void) setMinimumSessionDuration: (CDVInvokedUrlCommand*) command {
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void) setSessionTimeoutDuration: (CDVInvokedUrlCommand*) command {
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void) setUserId: (CDVInvokedUrlCommand*)command {
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Missing user id"];
    
    if ([command.arguments count] > 0) {
        NSString* userId = [command.arguments objectAtIndex:0];
        if (userId && userId.length > 0) {
            [FIRAnalytics setUserID:userId];
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        }
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void) setUserProperty: (CDVInvokedUrlCommand*)command {
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Missing user property name or user property value"];
    
    if ([command.arguments count] > 0) {
        NSDictionary *options = [command argumentAtIndex:0 withDefault:[NSNull null]];
        if (options) {
            NSString *userPropertyName = [options objectForKey:CDVGA_OPT_USER_PROPERTY_NAME];
            NSString *userPropertyValue = [options objectForKey:CDVGA_OPT_USER_PROPERTY_VALUE];
            if (userPropertyName && userPropertyName.length > 0 && userPropertyValue && userPropertyValue.length > 0) {
                [FIRAnalytics setUserPropertyString:userPropertyValue forName:userPropertyName];
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
            }
        }
    }
    
    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
}

@end
