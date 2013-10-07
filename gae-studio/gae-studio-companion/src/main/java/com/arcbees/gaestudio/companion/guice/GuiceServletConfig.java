package com.arcbees.gaestudio.companion.guice;

import com.arcbees.gaestudio.server.guice.GaeStudioModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class GuiceServletConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServerModule(), new GaeStudioModule());
    }
}
