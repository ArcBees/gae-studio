package com.arcbees.gaestudio.server.guice;

import com.arcbees.gaestudio.server.servlet.DataGenerator;
import com.google.inject.name.Names;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.ServletModule;
import com.gwtplatform.dispatch.server.guice.DispatchServiceImpl;
import com.gwtplatform.dispatch.shared.ActionImpl;

public class DispatchServletModule extends ServletModule {

    @Override
    public void configureServlets() {
        bind(Long.class).annotatedWith(Names.named("requestId")).toProvider(RequestIdProvider.class)
                .in(RequestScoped.class);

        serve("/" + ActionImpl.DEFAULT_SERVICE_NAME + "*").with(DispatchServiceImpl.class);
        
        serve("/dataGenerator").with(DataGenerator.class);
    }

}
