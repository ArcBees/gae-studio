/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.guice;

import com.arcbees.gaestudio.server.service.auth.SecureAuthModule;
import com.google.inject.servlet.ServletModule;

public class DebugGaeStudioDispatchServletModule extends ServletModule {
    private static final String GAE_STUDIO_HTML = "/gae-studio.*";

    private final String restPath;

    public DebugGaeStudioDispatchServletModule() {
        restPath = "";
    }

    public DebugGaeStudioDispatchServletModule(String restPath) {
        this.restPath = restPath;
    }

    @Override
    public void configureServlets() {
        install(new CommonModule(restPath));

        serve(GAE_STUDIO_HTML).with(RootServlet.class);
    }
}
