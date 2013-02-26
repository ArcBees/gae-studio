/**
 * Copyright 2013 ArcBees Inc.
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

import com.arcbees.gaestudio.server.servlet.DataGenerator;
import com.arcbees.gaestudio.server.servlet.EmbeddedStaticResourcesServlet;
import com.arcbees.gaestudio.shared.dispatch.util.GaeStudioActionImpl;
import com.google.inject.servlet.ServletModule;
import com.gwtplatform.dispatch.server.guice.DispatchServiceImpl;
import com.gwtplatform.dispatch.shared.ActionImpl;

public class DispatchServletModule extends ServletModule {
    public static final String EMBEDDED_PATH = "gae-studio-admin";

    @Override
    public void configureServlets() {
        // Standalone App Paths
        serve("/" + GaeStudioActionImpl.GAE_STUDIO + ActionImpl.DEFAULT_SERVICE_NAME + "*").with(
                DispatchServiceImpl.class);
        serve("/dataGenerator").with(DataGenerator.class);

        // Embedded App Paths
        serve("/gae-studio*/" + GaeStudioActionImpl.GAE_STUDIO + ActionImpl.DEFAULT_SERVICE_NAME + "*").with(
                DispatchServiceImpl.class);
        serveRegex("^/(gae-studio|module_gaestudio).*").with(EmbeddedStaticResourcesServlet.class);
    }
}
