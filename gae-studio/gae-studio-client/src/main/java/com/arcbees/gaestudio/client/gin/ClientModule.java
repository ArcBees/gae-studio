/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.gin;

import javax.inject.Singleton;

import com.arcbees.gaestudio.client.application.ApplicationModule;
import com.arcbees.gaestudio.client.formatters.BytesFormatter;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.resources.AppMessages;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.client.resources.CellTableResource;
import com.arcbees.gaestudio.client.rest.RestModule;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;
import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.gwtplatform.mvp.client.proxy.DefaultPlaceManager;

public class ClientModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new DefaultModule(DefaultPlaceManager.class));
        install(new DispatchAsyncModule());
        install(new ApplicationModule());
        install(new RestModule());

        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.visualizer);

        // TODO
        bindConstant().annotatedWith(ErrorPlace.class).to(NameTokens.visualizer);
        bindConstant().annotatedWith(UnauthorizedPlace.class).to(NameTokens.visualizer);

        bind(BytesFormatter.class).in(Singleton.class);
        bind(AppResources.class).in(Singleton.class);
        bind(AppConstants.class).in(Singleton.class);
        bind(AppMessages.class).in(Singleton.class);
        bind(CellTableResource.class).in(Singleton.class);
        
        bind(ResourceLoader.class).asEagerSingleton();
    }
}
