/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.guice;

import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.google.inject.servlet.ServletModule;

public class DebugGaeStudioDispatchServletModule extends ServletModule {
    private final String restPath;

    public DebugGaeStudioDispatchServletModule() {
        restPath = null;
    }

    public DebugGaeStudioDispatchServletModule(String restPath) {
        this.restPath = restPath;
    }

    @Override
    public void configureServlets() {
        String restEndPoint = EndPoints.REST_PATH;
        String filterPath = restPath == null ? "/" + restEndPoint : "/" + restPath + restEndPoint;

        install(new GaeServletModule(filterPath));
    }
}
