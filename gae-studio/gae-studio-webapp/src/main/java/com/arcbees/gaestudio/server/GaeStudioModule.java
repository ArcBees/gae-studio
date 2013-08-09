/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server;

import com.arcbees.gaestudio.server.guice.DispatchServletModule;
import com.arcbees.gaestudio.server.guice.RootServlet;
import com.google.inject.servlet.ServletModule;

public class GaeStudioModule extends ServletModule {
    private final String restPath;

    public GaeStudioModule() {
        this.restPath = null;
    }

    public GaeStudioModule(String restPath) {
        this.restPath = restPath;
    }

    @Override
    protected void configureServlets() {
        serve("/" + DispatchServletModule.EMBEDDED_PATH + "/").with(RootServlet.class);
        serve("/" + DispatchServletModule.EMBEDDED_PATH + "/gae-studio.*").with(RootServlet.class);
        serve("/" + DispatchServletModule.EMBEDDED_PATH + "*").with(EmbeddedStaticResourcesServlet.class);

        install(new DispatchServletModule(restPath));
    }
}
