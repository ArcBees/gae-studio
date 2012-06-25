package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import com.arcbees.core.client.mvp.uihandlers.ProviderUiHandlersStrategy;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
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
        bind(new TypeLiteral<UiHandlersStrategy<RequestFilterUiHandlers>>() {}).to(
                new TypeLiteral<ProviderUiHandlersStrategy<RequestFilterUiHandlers>>() {});
        bind(RequestFilterUiHandlers.class).to(RequestFilterPresenter.class);
    }

}
