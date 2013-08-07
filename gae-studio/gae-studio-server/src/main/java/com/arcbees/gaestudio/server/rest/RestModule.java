package com.arcbees.gaestudio.server.rest;

import com.google.inject.AbstractModule;

public class RestModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EntitiesResource.class);
        bind(NamespacesResource.class);
        bind(KindsResource.class);
    }
}
