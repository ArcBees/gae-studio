package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import com.google.inject.TypeLiteral;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class FilterModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        // Filters
        bindSingletonPresenterWidget(FiltersPresenter.class, FiltersPresenter.MyView.class,
                FiltersView.class);

        // By Request
        bindSingletonPresenterWidget(RequestFilterPresenter.class, RequestFilterPresenter.MyView.class,
                RequestFilterView.class);

        // By class/method
        bindSingletonPresenterWidget(MethodFilterPresenter.class, MethodFilterPresenter.MyView.class,
                MethodFilterView.class);

        // By type
        bindSingletonPresenterWidget(TypeFilterPresenter.class, TypeFilterPresenter.MyView.class, TypeFilterView.class);
    }

}
