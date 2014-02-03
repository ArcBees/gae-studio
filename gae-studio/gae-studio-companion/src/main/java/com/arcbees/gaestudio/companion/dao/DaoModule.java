/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.companion.dao;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;

public class DaoModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CarDao.class).in(Singleton.class);
        bind(StringIdEntityDao.class).in(Singleton.class);
    }
}
