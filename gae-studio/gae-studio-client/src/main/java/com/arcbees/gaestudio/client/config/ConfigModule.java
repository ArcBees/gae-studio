/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.config;

import javax.inject.Singleton;

import com.arcbees.gaestudio.shared.config.AppConfig;
import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;

public class ConfigModule extends AbstractGinModule {
    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    AppConfig appConfig() {
        String json = readGlobalObject(AppConfig.OBJECT_KEY);
        AppConfigMapper appConfigMapper = GWT.create(AppConfigMapper.class);

        return appConfigMapper.read(json);
    }

    private native String readGlobalObject(String objectKey) /*-{
        var obj = $wnd[objectKey];
        return $wnd.JSON.stringify(obj);
    }-*/;
}
