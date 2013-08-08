package com.arcbees.gaestudio.server.rest;

import java.util.UUID;

import javax.inject.Singleton;

import com.arcbees.gaestudio.server.GaConstants;
import com.arcbees.googleanalytic.GoogleAnalytic;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class RestModule extends AbstractModule {
    // TODO: Generate this only once per application through a propertie file.
    private static final String CLIENT_ID = UUID.randomUUID().toString();
    private static final String TRACKING_CODE = "UA-41550930-4";
    private static final String APPLICATION_NAME = "GAE-Studio";
    private static final String APPLICATION_VERSION = "1.0";

    @Override
    protected void configure() {
        bind(EntitiesResource.class);
        bind(NamespacesResource.class);
        bind(KindsResource.class);
    }

    @Provides
    @Singleton
    GoogleAnalytic createGoogleAnalytic() {
        GoogleAnalytic googleAnalytic
                = GoogleAnalytic.build(CLIENT_ID, TRACKING_CODE, APPLICATION_NAME, APPLICATION_VERSION);

        googleAnalytic.trackEvent(GaConstants.CAT_INITIALIZATION, GaConstants.APPLICATION_LOADED);

        return googleAnalytic;
    }
}
