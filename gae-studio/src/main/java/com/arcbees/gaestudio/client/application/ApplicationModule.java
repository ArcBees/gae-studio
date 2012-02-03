package com.arcbees.gaestudio.client.application;

import com.arcbees.gaestudio.client.application.entitykindlist.EntityKindListModule;
import com.arcbees.gaestudio.client.application.home.HomeModule;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new HomeModule());
        install(new EntityKindListModule());

        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
                ApplicationPresenter.MyProxy.class);

    }
}
