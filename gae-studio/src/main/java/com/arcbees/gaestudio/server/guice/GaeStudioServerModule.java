package com.arcbees.gaestudio.server.guice;

import com.arcbees.gaestudio.server.domain.Car;
import com.arcbees.gaestudio.server.domain.Driver;
import com.googlecode.objectify.ObjectifyService;
import com.gwtplatform.dispatch.server.guice.HandlerModule;

public class GaeStudioServerModule extends HandlerModule {
    @Override
    protected void configureHandlers() {
        ObjectifyService.register(Driver.class);
        ObjectifyService.register(Car.class);
        
        install(new DispatchServletModule());
        install(new GaeStudioModule());
    }
}
