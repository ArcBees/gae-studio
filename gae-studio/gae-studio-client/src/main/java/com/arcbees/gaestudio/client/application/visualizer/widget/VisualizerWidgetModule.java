/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.gaestudio.client.application.visualizer.widget.entity.EntityWidgetModule;
import com.arcbees.gaestudio.client.application.visualizer.widget.namespace.NamespaceWidgetModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class VisualizerWidgetModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new NamespaceWidgetModule());
        install(new EntityWidgetModule());

        bindPresenterWidget(ImportPresenter.class, ImportPresenter.MyView.class, ImportView.class);

        bindSingletonPresenterWidget(EntityListPresenter.class, EntityListPresenter.MyView.class,
                EntityListView.class);

        bind(EntityDeletionPresenter.class).asEagerSingleton();
        bind(EntityDeletionPresenter.MyView.class).to(EntityDeletionView.class);

        install(new GinFactoryModuleBuilder().build(UploadFormFactory.class));
    }
}
