/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.guice;

import com.arcbees.gaestudio.server.domain.Car;
import com.arcbees.gaestudio.server.domain.Driver;
import com.googlecode.objectify.ObjectifyService;
import com.gwtplatform.dispatch.server.guice.HandlerModule;

public class GaeStudioServerModule extends HandlerModule {
    @Override
    protected void configureHandlers() {
        ObjectifyService.register(Driver.class);
        ObjectifyService.register(Car.class);

        install(new DispatchServletModule());
        install(new GaeStudioModule());
    }
}
