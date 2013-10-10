/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.gaestudio.client.application.visualizer.widget.namespace.NamespaceWidgetModule;
import com.arcbees.gaestudio.client.application.visualizer.widget.entity.PropertyWidgetModule;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class VisualizerWidgetModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new NamespaceWidgetModule());
        install(new PropertyWidgetModule());

        bindSingletonPresenterWidget(EntityListPresenter.class, EntityListPresenter.MyView.class,
                EntityListView.class);
        bindSingletonPresenterWidget(VisualizerToolbarPresenter.class, VisualizerToolbarPresenter.MyView.class,
                VisualizerToolbarView.class);

        bind(EntityDetailsPresenter.class).asEagerSingleton();
        bind(EntityDetailsPresenter.MyView.class).to(EntityDetailsView.class);

        bind(EntityDeletionPresenter.class).asEagerSingleton();
        bind(EntityDeletionPresenter.MyView.class).to(EntityDeletionView.class);
    }
}
