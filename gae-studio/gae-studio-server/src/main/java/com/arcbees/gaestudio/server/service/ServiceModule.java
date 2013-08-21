/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EntitiesService.class).to(EntitiesServiceImpl.class).in(Singleton.class);
        bind(EntityService.class).to(EntityServiceImpl.class).in(Singleton.class);
        bind(KindsService.class).to(KindsServiceImpl.class).in(Singleton.class);
        bind(NamespacesService.class).to(NamespacesServiceImpl.class).in(Singleton.class);
    }
}
