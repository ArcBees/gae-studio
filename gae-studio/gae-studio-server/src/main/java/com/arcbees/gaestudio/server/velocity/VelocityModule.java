package com.arcbees.gaestudio.server.velocity;

import javax.inject.Singleton;

import org.apache.velocity.app.VelocityEngine;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class VelocityModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().build(VelocityWrapperFactory.class));
    }

    @Provides
    @Singleton
    VelocityEngine buildVelocityEngine() {
        VelocityEngine velocityEngine = new VelocityEngine();

        velocityEngine.setProperty("resource.loader", "class");
        velocityEngine.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        velocityEngine.init();

        return velocityEngine;
    }
}
