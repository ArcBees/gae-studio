/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.analytic;

import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import com.arcbees.gaestudio.server.AnalyticsTrackingIds;
import com.arcbees.gaestudio.server.BuildConstants;
import com.arcbees.googleanalytic.GoogleAnalytic;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;

public class AnalyticModule extends AbstractModule {
    private static final String APPLICATION_NAME = "GAE-Studio";
    private static final String APPLICATION_VERSION = BuildConstants.VERSION;

    @Override
    protected void configure() {
        ApiMethodInterceptor apiMethodInterceptor = new ApiMethodInterceptor();
        requestInjection(apiMethodInterceptor);

        Matcher<Class> packageMatcher = Matchers.inSubpackage("com.arcbees.gaestudio.server.api");

        bindInterceptor(packageMatcher, Matchers.annotatedWith(GET.class), apiMethodInterceptor);
        bindInterceptor(packageMatcher, Matchers.annotatedWith(POST.class), apiMethodInterceptor);
        bindInterceptor(packageMatcher, Matchers.annotatedWith(PUT.class), apiMethodInterceptor);
        bindInterceptor(packageMatcher, Matchers.annotatedWith(DELETE.class), apiMethodInterceptor);
    }

    @Provides
    @Singleton
    GoogleAnalytic createGoogleAnalytic(GoogleAnalyticClientIdProvider clientIdProvider) {
        GoogleAnalytic googleAnalytic
                = GoogleAnalytic.build(clientIdProvider.get(), AnalyticsTrackingIds.SERVER_TRACKING_ID,
                APPLICATION_NAME, APPLICATION_VERSION);

        googleAnalytic.trackEvent(GoogleAnalyticConstants.CAT_INITIALIZATION,
                GoogleAnalyticConstants.APPLICATION_LOADED);

        return googleAnalytic;
    }
}
