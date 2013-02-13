package com.arcbees.gaestudio.client.gin;

import javax.inject.Singleton;

import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.ApplicationModule;
import com.arcbees.gaestudio.client.formatters.BytesFormatter;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;
import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.gwtplatform.mvp.client.proxy.DefaultPlaceManager;

public class ClientModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new DefaultModule(DefaultPlaceManager.class));
        install(new DispatchAsyncModule());
        install(new ApplicationModule());

        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.profiler);
        
        // TODO
        bindConstant().annotatedWith(ErrorPlace.class).to(NameTokens.profiler);
        bindConstant().annotatedWith(UnauthorizedPlace.class).to(NameTokens.profiler);

        bind(BytesFormatter.class).in(Singleton.class);
        
        // Resources
        bind(Resources.class).in(Singleton.class);
        bind(ResourceLoader.class).asEagerSingleton();
    }
}
