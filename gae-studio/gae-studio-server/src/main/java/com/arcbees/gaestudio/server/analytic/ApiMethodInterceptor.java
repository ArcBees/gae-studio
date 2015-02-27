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

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.arcbees.gaestudio.server.util.PackageHelper;
import com.arcbees.googleanalytic.GoogleAnalytic;

class ApiMethodInterceptor implements MethodInterceptor {
    @Inject
    protected GoogleAnalytic googleAnalytic;
    @Inject
    private Provider<HttpServletRequest> httpServletRequestProvider;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        HttpServletRequest request = httpServletRequestProvider.get();

        Package resourcePackage = invocation.getMethod().getDeclaringClass().getPackage();
        String label = PackageHelper.getLeafPackageName(resourcePackage);

        String path = request.getRequestURI();
        String event = String.format("%s %s", request.getMethod(), path);

        googleAnalytic.trackEvent(GoogleAnalyticConstants.CAT_SERVER_CALL, event, label);

        return invocation.proceed();
    }
}
