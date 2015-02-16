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

package com.arcbees.gaestudio.server.guice;

import com.arcbees.gaestudio.server.EmbeddedStaticResourcesServlet;
import com.google.inject.servlet.ServletModule;

public class EmbeddedPathModule extends ServletModule {
    private static final String EMBEDDED_PATH = "gae-studio-admin";
    private static final String GAE_STUDIO_HTML = "/gae-studio.*";

    @Override
    protected void configureServlets() {
        serve("/", "/" + GAE_STUDIO_HTML).with(RootServlet.class);
        serve("/" + EMBEDDED_PATH + "/", "/" + EMBEDDED_PATH + GAE_STUDIO_HTML).with(RootServlet.class);
        serve("/" + EMBEDDED_PATH + "/*").with(EmbeddedStaticResourcesServlet.class);
    }
}
