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

