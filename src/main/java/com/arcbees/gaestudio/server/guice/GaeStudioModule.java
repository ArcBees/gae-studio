package com.arcbees.gaestudio.server.guice;

import com.arcbees.gaestudio.server.dispatch.GaeStudioDispatchModule;
import com.arcbees.gaestudio.server.recorder.GaeStudioRecorderModule;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;

public class GaeStudioModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new GaeStudioRecorderModule());
        install(new GaeStudioDispatchModule());
    }

    public static void initialize(Injector injector) {
        GaeStudioRecorderModule.initialize(injector);
    }

}
