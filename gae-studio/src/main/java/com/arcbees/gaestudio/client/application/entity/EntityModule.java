package com.arcbees.gaestudio.client.application.entity;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class EntityModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(EntityPresenter.class, EntityPresenter.MyView.class, EntityView.class,
                EntityPresenter.MyProxy.class);
    }
}
