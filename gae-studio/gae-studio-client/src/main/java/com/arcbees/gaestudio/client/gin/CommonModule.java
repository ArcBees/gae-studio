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

package com.arcbees.gaestudio.client.gin;

import javax.inject.Singleton;
import javax.validation.Validation;
import javax.validation.Validator;

import com.arcbees.gaestudio.client.application.ApplicationModule;
import com.arcbees.gaestudio.client.application.version.VersionModule;
import com.arcbees.gaestudio.client.config.ConfigModule;
import com.arcbees.gaestudio.client.formatters.BytesFormatter;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.resources.AppMessages;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.client.resources.CellTableResource;
import com.arcbees.gaestudio.client.rest.RestModule;
import com.arcbees.gaestudio.client.ui.UiModule;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.client.util.keyprettifier.KeyPrettifierModule;
import com.google.inject.Provides;
import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

public class CommonModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new DefaultModule.Builder().build());
        install(new ApplicationModule());
        install(new RestModule());
        install(new ConfigModule());
        install(new VersionModule());
        install(new UiModule());
        install(new KeyPrettifierModule());

        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.visualizer);
        bindConstant().annotatedWith(ErrorPlace.class).to(NameTokens.visualizer);
        bindConstant().annotatedWith(UnauthorizedPlace.class).to(NameTokens.visualizer);

        bind(BytesFormatter.class).in(Singleton.class);
        bind(AppResources.class).in(Singleton.class);
        bind(AppConstants.class).in(Singleton.class);
        bind(AppMessages.class).in(Singleton.class);
        bind(CellTableResource.class).in(Singleton.class);

        bind(ResourceLoader.class).asEagerSingleton();

        requestStaticInjection(AsyncCallbackImpl.class);
    }

    @Provides
    Validator getValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
}
