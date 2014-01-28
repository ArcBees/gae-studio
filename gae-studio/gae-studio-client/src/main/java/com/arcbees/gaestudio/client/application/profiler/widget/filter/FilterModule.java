/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class FilterModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new GinFactoryModuleBuilder().build(FilterDropDownFactory.class));

        bindSingletonPresenterWidget(FiltersPresenter.class, FiltersPresenter.MyView.class,
                FiltersView.class);
        bindSingletonPresenterWidget(RequestFilterPresenter.class, RequestFilterPresenter.MyView.class,
                RequestFilterView.class);
        bindSingletonPresenterWidget(MethodFilterPresenter.class, MethodFilterPresenter.MyView.class,
                MethodFilterView.class);
        bindSingletonPresenterWidget(TypeFilterPresenter.class, TypeFilterPresenter.MyView.class, TypeFilterView.class);
    }
}
