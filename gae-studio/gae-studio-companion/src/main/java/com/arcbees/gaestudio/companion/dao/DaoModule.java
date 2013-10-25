package com.arcbees.gaestudio.companion.dao;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;

public class DaoModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CarDao.class).in(Singleton.class);
    }
}
