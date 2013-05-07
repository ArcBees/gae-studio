/**
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.gaestudio.client.application.visualizer;

import com.arcbees.gaestudio.client.application.visualizer.sidebar.SidebarModule;
import com.arcbees.gaestudio.client.application.visualizer.ui.VisualizerUiFactory;
import com.arcbees.gaestudio.client.application.visualizer.widget.EntityDetailsPresenter;
import com.arcbees.gaestudio.client.application.visualizer.widget.EntityDetailsView;
import com.arcbees.gaestudio.client.application.visualizer.widget.EntityListPresenter;
import com.arcbees.gaestudio.client.application.visualizer.widget.EntityListView;
import com.arcbees.gaestudio.client.application.visualizer.widget.VisualizerToolbarPresenter;
import com.arcbees.gaestudio.client.application.visualizer.widget.VisualizerToolbarView;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class VisualizerModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new GinFactoryModuleBuilder().build(VisualizerUiFactory.class));
        install(new SidebarModule());

        bindPresenter(VisualizerPresenter.class, VisualizerPresenter.MyView.class,
                VisualizerView.class, VisualizerPresenter.MyProxy.class);
        bindSingletonPresenterWidget(EntityListPresenter.class, EntityListPresenter.MyView.class,
                EntityListView.class);
        bindSingletonPresenterWidget(VisualizerToolbarPresenter.class, VisualizerToolbarPresenter.MyView.class,
                VisualizerToolbarView.class);

        bind(EntityDetailsPresenter.class).asEagerSingleton();
        bind(EntityDetailsPresenter.MyView.class).to(EntityDetailsView.class);
    }
}