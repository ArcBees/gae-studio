package com.arcbees.gaestudio.server.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class GuiceServletConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        Injector injector = Guice.createInjector(new ServerModule(), new DispatchServletModule());

        GaeStudioModule.initialize(injector);

        return injector;
    }

}