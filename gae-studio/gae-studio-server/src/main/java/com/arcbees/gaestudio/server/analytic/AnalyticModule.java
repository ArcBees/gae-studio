/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
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

import com.arcbees.googleanalytic.GoogleAnalytic;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;

public class AnalyticModule extends AbstractModule {
    private static final String TRACKING_CODE = "UA-41550930-4";
    private static final String APPLICATION_NAME = "GAE-Studio";
    private static final String APPLICATION_VERSION = "0.3";

    @Override
    protected void configure() {
        RestMethodInterceptor restMethodInterceptor = new RestMethodInterceptor();
        requestInjection(restMethodInterceptor);

        Matcher<Class> packageMatcher = Matchers.inPackage(Package.getPackage("com.arcbees.gaestudio.server.rest"));

        bindInterceptor(packageMatcher, Matchers.annotatedWith(GET.class), restMethodInterceptor);
        bindInterceptor(packageMatcher, Matchers.annotatedWith(POST.class), restMethodInterceptor);
        bindInterceptor(packageMatcher, Matchers.annotatedWith(PUT.class), restMethodInterceptor);
        bindInterceptor(packageMatcher, Matchers.annotatedWith(DELETE.class), restMethodInterceptor);
    }

    @Provides
    @Singleton
    GoogleAnalytic createGoogleAnalytic(GoogleAnalyticClientIdProvider clientIdProvider) {
        GoogleAnalytic googleAnalytic
                = GoogleAnalytic.build(clientIdProvider.get(), TRACKING_CODE, APPLICATION_NAME, APPLICATION_VERSION);

        googleAnalytic.trackEvent(GoogleAnalyticConstants.CAT_INITIALIZATION,
                GoogleAnalyticConstants.APPLICATION_LOADED);

        return googleAnalytic;
    }
}
