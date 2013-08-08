package com.arcbees.gaestudio.server.guice;

import com.arcbees.gaestudio.server.recorder.GaeStudioRecorderModule;
import com.arcbees.gaestudio.server.rest.RestModule;
import com.google.inject.servlet.ServletModule;

public class GaeServletModule extends ServletModule {
    private final String restPath;

    GaeServletModule(String restPath) {
        this.restPath = restPath;
    }

    @Override
    protected void configureServlets() {
        install(new GaeStudioRecorderModule());
        install(new RestModule());

        filter(restPath.replace("//", "/") + "*").through(GuiceRestEasyFilterDispatcher.class);

        bindConstant().annotatedWith(BaseRestPath.class).to(restPath);
    }
}
