package com.arcbees.gaestudio.client.gin;

import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.ApplicationModule;
import com.arcbees.gaestudio.client.place.ClientPlaceManager;
import com.arcbees.gaestudio.client.place.DefaultPlace;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

import javax.inject.Singleton;

public class ClientModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        install(new DefaultModule(ClientPlaceManager.class));
        install(new ApplicationModule());

        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.profiler);

        bind(Resources.class).in(Singleton.class);
    }
}
