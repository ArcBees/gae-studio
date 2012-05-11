/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.server.recorder.authentication;

import com.google.inject.AbstractModule;
import com.google.inject.servlet.RequestScoped;

import javax.inject.Singleton;

public class AuthenticationModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ListenerProvider.class).in(Singleton.class);
        bind(Listener.class).toProvider(ListenerProvider.class).in(RequestScoped.class);
    }

}
