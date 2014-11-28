/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer;

import com.arcbees.gaestudio.client.application.visualizer.columnfilter.ColumnFilterModule;
import com.arcbees.gaestudio.client.application.visualizer.sidebar.SidebarModule;
import com.arcbees.gaestudio.client.application.visualizer.widget.VisualizerWidgetModule;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class VisualizerModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new SidebarModule());
        install(new VisualizerWidgetModule());
        install(new ColumnFilterModule());

        bindPresenter(VisualizerPresenter.class, VisualizerPresenter.MyView.class,
                VisualizerView.class, VisualizerPresenter.MyProxy.class);
    }
}
