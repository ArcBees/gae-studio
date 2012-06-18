package com.arcbees.gaestudio.server.guice;

import com.arcbees.gaestudio.server.domain.Complex;
import com.arcbees.gaestudio.server.domain.Sprocket;
import com.googlecode.objectify.ObjectifyService;
import com.gwtplatform.dispatch.server.guice.HandlerModule;

public class ServerModule extends HandlerModule {

    @Override
    protected void configureHandlers() {
        ObjectifyService.register(Sprocket.class);
        ObjectifyService.register(Complex.class);
        install(new GaeStudioModule());
    }

}
