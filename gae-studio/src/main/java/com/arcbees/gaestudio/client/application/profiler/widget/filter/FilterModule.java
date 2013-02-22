package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class FilterModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindSingletonPresenterWidget(FiltersPresenter.class, FiltersPresenter.MyView.class,
                FiltersView.class);
        bindSingletonPresenterWidget(RequestFilterPresenter.class, RequestFilterPresenter.MyView.class,
                RequestFilterView.class);
        bindSingletonPresenterWidget(MethodFilterPresenter.class, MethodFilterPresenter.MyView.class,
                MethodFilterView.class);
        bindSingletonPresenterWidget(TypeFilterPresenter.class, TypeFilterPresenter.MyView.class, TypeFilterView.class);
    }
}
