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
