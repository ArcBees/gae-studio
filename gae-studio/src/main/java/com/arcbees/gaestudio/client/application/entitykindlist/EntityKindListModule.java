package com.arcbees.gaestudio.client.application.entitykindlist;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class EntityKindListModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bindPresenter(EntityKindListPresenter.class, EntityKindListPresenter.MyView.class,
                EntityKindListView.class, EntityKindListPresenter.MyProxy.class);
    }

}
