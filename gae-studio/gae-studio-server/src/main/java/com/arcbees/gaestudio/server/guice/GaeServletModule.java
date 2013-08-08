package com.arcbees.gaestudio.server.guice;

import com.arcbees.gaestudio.server.recorder.GaeStudioRecorderModule;
import com.arcbees.gaestudio.server.rest.RestModule;
import com.arcbees.gaestudio.server.velocity.VelocityModule;
import com.arcbees.gaestudio.shared.BaseRestPath;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.google.inject.servlet.ServletModule;

public class GaeServletModule extends ServletModule {
    private final String restPath;

    GaeServletModule(String restPath) {
        this.restPath = restPath.replace("//", "/");
    }

    @Override
    protected void configureServlets() {
        install(new GaeStudioRecorderModule());
        install(new VelocityModule());
        install(new RestModule());

        bindConstant().annotatedWith(BaseRestPath.class).to(restPath);

        String fullRestPath = (restPath + "/" + EndPoints.REST_PATH).replace("//", "/");

        filter(fullRestPath + "*").through(GuiceRestEasyFilterDispatcher.class);
    }
}
