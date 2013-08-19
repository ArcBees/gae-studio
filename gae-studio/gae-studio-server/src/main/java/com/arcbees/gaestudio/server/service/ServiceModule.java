package com.arcbees.gaestudio.server.service;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EntitiesService.class).to(EntitiesServiceImpl.class).in(Singleton.class);
    }
}
