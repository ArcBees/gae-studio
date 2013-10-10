package com.arcbees.gaestudio.companion.rest;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;

public class RestModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(HelloResource.class).in(Singleton.class);
        bind(ClearResource.class).in(Singleton.class);
    }
}
