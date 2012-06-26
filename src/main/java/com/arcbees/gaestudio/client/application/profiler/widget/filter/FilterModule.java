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
        bind(new TypeLiteral<UiHandlersStrategy<FiltersUiHandlers>>() {}).to(
                new TypeLiteral<ProviderUiHandlersStrategy<FiltersUiHandlers>>() {});
        bind(FiltersUiHandlers.class).to(FiltersPresenter.class);

        // By Request
        bindSingletonPresenterWidget(RequestFilterPresenter.class, RequestFilterPresenter.MyView.class,
                RequestFilterView.class);
        bind(new TypeLiteral<UiHandlersStrategy<RequestFilterUiHandlers>>() {}).to(
                new TypeLiteral<ProviderUiHandlersStrategy<RequestFilterUiHandlers>>() {});
        bind(RequestFilterUiHandlers.class).to(RequestFilterPresenter.class);

        // By class/method
        bindSingletonPresenterWidget(MethodFilterPresenter.class, MethodFilterPresenter.MyView.class,
                MethodFilterView.class);
        bind(new TypeLiteral<UiHandlersStrategy<MethodFilterUiHandlers>>() {}).to(
                new TypeLiteral<ProviderUiHandlersStrategy<MethodFilterUiHandlers>>() {});
        bind(MethodFilterUiHandlers.class).to(MethodFilterPresenter.class);

        // By type
        bindSingletonPresenterWidget(TypeFilterPresenter.class, TypeFilterPresenter.MyView.class, TypeFilterView.class);
        bind(new TypeLiteral<UiHandlersStrategy<TypeFilterUiHandlers>>() {}).to(
                new TypeLiteral<ProviderUiHandlersStrategy<TypeFilterUiHandlers>>() {});
        bind(TypeFilterUiHandlers.class).to(TypeFilterPresenter.class);
    }

}
