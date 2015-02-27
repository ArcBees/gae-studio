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
        install(new RestDispatchAsyncModule.Builder().dispatchHooks(DispatchHooksImpl.class).build());
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
