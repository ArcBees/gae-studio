/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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
