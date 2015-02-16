/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.gaestudio.server.analytic;

import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import com.arcbees.gaestudio.server.BuildConstants;
import com.arcbees.gaestudio.shared.AnalyticsTrackingIds;
import com.arcbees.googleanalytic.GoogleAnalytic;
import com.google.appengine.api.utils.SystemProperty;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;

import static com.google.appengine.api.utils.SystemProperty.Environment.Value.Development;

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

        boolean isDevelopmentEnvironment = SystemProperty.environment.value().equals(Development);
        bindConstant().annotatedWith(UseCookieDomainNone.class).to(isDevelopmentEnvironment);
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
