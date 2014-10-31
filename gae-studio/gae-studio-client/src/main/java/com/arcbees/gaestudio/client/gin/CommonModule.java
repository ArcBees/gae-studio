/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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
import com.arcbees.gaestudio.client.util.CurrentUser;
import com.arcbees.gaestudio.client.util.KeyPrettifier.KeyPrettifierModule;
import com.google.inject.Provides;
import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.gwtplatform.mvp.client.proxy.DefaultPlaceManager;

public class CommonModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new DefaultModule(DefaultPlaceManager.class));
        install(new ApplicationModule());
        install(new RestModule());
        install(new ConfigModule());
        install(new VersionModule());
        install(new UiModule());
        install(new KeyPrettifierModule());

        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.visualizer);

        bindConstant().annotatedWith(ErrorPlace.class).to(NameTokens.visualizer);
        bindConstant().annotatedWith(UnauthorizedPlace.class).to(NameTokens.auth);

        bind(CurrentUser.class).asEagerSingleton();

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
