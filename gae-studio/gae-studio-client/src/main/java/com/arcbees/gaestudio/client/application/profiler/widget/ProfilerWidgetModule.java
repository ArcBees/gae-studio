/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.widget;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ProfilerWidgetModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindSingletonPresenterWidget(DetailsPresenter.class, DetailsPresenter.MyView.class,
                DetailsView.class);
        bindSingletonPresenterWidget(StatementPresenter.class, StatementPresenter.MyView.class,
                StatementView.class);
        bindSingletonPresenterWidget(StatisticsPresenter.class, StatisticsPresenter.MyView.class,
                StatisticsView.class);
        bindSingletonPresenterWidget(ProfilerToolbarPresenter.class, ProfilerToolbarPresenter.MyView.class,
                ProfilerToolbarView.class);

        bind(StatementUiHandlers.class).to(StatementPresenter.class);
        bind(ProfilerToolbarUiHandlers.class).to(ProfilerToolbarPresenter.class);
    }
}
