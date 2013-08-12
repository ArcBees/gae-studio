/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.rest;

import java.util.UUID;

import javax.inject.Singleton;

import com.arcbees.gaestudio.server.GoogleAnalyticConstants;
import com.arcbees.googleanalytic.GoogleAnalytic;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class RestModule extends AbstractModule {
    // TODO: Generate this only once per application through a properties file.
    private static final String CLIENT_ID = UUID.randomUUID().toString();
    private static final String TRACKING_CODE = "UA-41550930-4";
    private static final String APPLICATION_NAME = "GAE-Studio";
    private static final String APPLICATION_VERSION = "1.0";

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().build(SubresourceFactory.class));

        bind(NamespacesResource.class);
        bind(KindsResource.class);
        bind(EntitiesResource.class);
        bind(OperationsResource.class);
        bind(RecordResource.class);
    }

    @Provides
    @Singleton
    GoogleAnalytic createGoogleAnalytic() {
        GoogleAnalytic googleAnalytic
                = GoogleAnalytic.build(CLIENT_ID, TRACKING_CODE, APPLICATION_NAME, APPLICATION_VERSION);

        googleAnalytic.trackEvent(GoogleAnalyticConstants.CAT_INITIALIZATION, GoogleAnalyticConstants.APPLICATION_LOADED);

        return googleAnalytic;
    }
}
