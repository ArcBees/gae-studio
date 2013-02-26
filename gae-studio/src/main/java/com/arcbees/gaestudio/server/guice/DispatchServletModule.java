package com.arcbees.gaestudio.server.guice;

import com.arcbees.gaestudio.server.servlet.DataGenerator;
import com.arcbees.gaestudio.server.servlet.StaticResourcesServlet;
import com.arcbees.gaestudio.shared.dispatch.util.GaeStudioActionImpl;
import com.google.inject.servlet.ServletModule;
import com.gwtplatform.dispatch.server.guice.DispatchServiceImpl;
import com.gwtplatform.dispatch.shared.ActionImpl;

public class DispatchServletModule extends ServletModule {
    public static final String EMBEDDED_PATH = "gae-studio-admin";

    @Override
    public void configureServlets() {
        // Standalone App Paths
        serve("/" + GaeStudioActionImpl.GAE_STUDIO + ActionImpl.DEFAULT_SERVICE_NAME + "*").with(
                DispatchServiceImpl.class);
        serve("/dataGenerator").with(DataGenerator.class);
        
        // Embedded App Paths
        serve("/" + EMBEDDED_PATH + "/" + GaeStudioActionImpl.GAE_STUDIO + ActionImpl.DEFAULT_SERVICE_NAME + "*").with(
                DispatchServiceImpl.class);
        serve("/" + EMBEDDED_PATH + "*").with(StaticResourcesServlet.class);
    }
}
