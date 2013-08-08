/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.guice;

import com.google.inject.servlet.ServletModule;

public class DispatchServletModule extends ServletModule {
    public static final String EMBEDDED_PATH = "gae-studio-admin";

    private final String restPath;

    public DispatchServletModule(String restPath) {
        this.restPath = restPath;
    }

    @Override
    public void configureServlets() {
        String restEndPoint = EMBEDDED_PATH;
        String baseRestPath = restPath == null ? "/" + restEndPoint : "/" + restPath + restEndPoint;

        install(new GaeServletModule(baseRestPath));
    }
}
