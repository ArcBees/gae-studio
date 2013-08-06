package com.arcbees.gaestudio.client.rest;

import javax.inject.Singleton;

import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;

public class RestModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(ResourceFactory.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    EntitiesService getEntitiesService(ResourceFactory resourceFactory) {
        return resourceFactory.setupProxy(GWT.<EntitiesService>create(EntitiesService.class), EndPoints.ENTITIES);
    }
}
