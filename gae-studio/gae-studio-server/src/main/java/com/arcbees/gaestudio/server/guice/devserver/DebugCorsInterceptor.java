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

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import com.arcbees.gaestudio.server.guice.GaeStudioResource;

/**
 * This class will only be bound when using Dev server and allows Blob upload from any address (127.0.0.1 vs localhost
 * vs hostname).
 */
@Provider
@GaeStudioResource
public class DebugCorsInterceptor implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext,
            ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
    }
}
