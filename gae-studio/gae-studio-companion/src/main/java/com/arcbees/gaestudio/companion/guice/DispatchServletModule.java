/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.companion.guice;

import javax.inject.Singleton;

import com.arcbees.guicyresteasy.GuiceRestEasyFilterDispatcher;
import com.google.inject.servlet.ServletModule;
import com.googlecode.objectify.ObjectifyFilter;

public class DispatchServletModule extends ServletModule {
    @Override
    protected void configureServlets() {
        filter("/*").through(ObjectifyFilter.class);
        filter("/rest/*").through(GuiceRestEasyFilterDispatcher.class);

        bind(ObjectifyFilter.class).in(Singleton.class);
    }
}

