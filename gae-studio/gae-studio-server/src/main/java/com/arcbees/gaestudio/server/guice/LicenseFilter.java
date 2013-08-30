/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.guice;

import java.io.IOException;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.arcbees.gaestudio.shared.ExpirationDate;

@Singleton
public class LicenseFilter implements Filter {
    private final Long expirationDateTimeMillis;

    @Inject
    LicenseFilter(@ExpirationDate Date expirationDate) {
        this.expirationDateTimeMillis = expirationDate.getTime();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (new Date().getTime() < expirationDateTimeMillis) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Thanks for using GAE Studio. Your version" +
                    " is now expired. Please update it via our website.");
        }
    }

    @Override
    public void destroy() {
    }
}
