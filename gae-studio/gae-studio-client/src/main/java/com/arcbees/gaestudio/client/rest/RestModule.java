/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.rest;

import javax.inject.Singleton;

import com.arcbees.gaestudio.shared.config.AppConfig;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;
import com.gwtplatform.dispatch.rest.client.RestApplicationPath;
import com.gwtplatform.dispatch.rest.client.gin.RestDispatchAsyncModule;

public class RestModule extends AbstractGinModule {
    @Override
    protected void configure() {
        install(new RestDispatchAsyncModule());
    }

    @Provides
    @RestApplicationPath
    @Singleton
    private String getRestApplicationPath(AppConfig appConfig) {
        String restPath = appConfig.getRestPath();

        if (restPath.endsWith("/")) {
            restPath = restPath.substring(0, restPath.length() - 1);
        }

        return restPath;
    }
}
