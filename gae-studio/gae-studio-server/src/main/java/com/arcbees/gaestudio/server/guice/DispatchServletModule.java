/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.guice;

import com.arcbees.gaestudio.server.dispatch.GaeStudioDispatchModule;
import com.arcbees.gaestudio.server.recorder.GaeStudioRecorderModule;
import com.arcbees.gaestudio.server.rest.RestModule;
import com.arcbees.gaestudio.shared.dispatch.util.GaeStudioActionImpl;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.guicyresteasy.GuiceRestEasyFilterDispatcher;
import com.google.inject.servlet.ServletModule;
import com.gwtplatform.dispatch.server.guice.DispatchServiceImpl;
import com.gwtplatform.dispatch.shared.ActionImpl;

public class DispatchServletModule extends ServletModule {
    public static final String EMBEDDED_PATH = "gae-studio-admin";

    @Override
    public void configureServlets() {
        serve("/" + EMBEDDED_PATH + "/" + GaeStudioActionImpl.GAE_STUDIO + ActionImpl.DEFAULT_SERVICE_NAME + "*").with(
                DispatchServiceImpl.class);

        filter("/" + EMBEDDED_PATH + "/" + EndPoints.REST_PATH + "*").through(GuiceRestEasyFilterDispatcher.class);

        install(new GaeStudioRecorderModule());
        install(new GaeStudioDispatchModule());
        install(new RestModule());
    }
}
