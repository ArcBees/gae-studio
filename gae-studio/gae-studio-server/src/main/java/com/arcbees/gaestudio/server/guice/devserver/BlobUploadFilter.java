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

package com.arcbees.gaestudio.server.guice.devserver;

import java.io.IOException;

import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * This class will only be bound when using Dev server and allows Blob upload from any address (127.0.0.1 vs localhost
 * vs hostname).
 */
@Singleton
public class BlobUploadFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(request, response);

        if (response instanceof HttpServletResponse) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        }
    }

    @Override
    public void destroy() {
    }
}
