package com.arcbees.gaestudio.client.application.widget;

import com.arcbees.core.client.mvp.uihandlers.ProviderUiHandlersStrategy;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.google.inject.TypeLiteral;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class HeaderModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bindSingletonPresenterWidget(HeaderPresenter.class, HeaderPresenter.MyView.class,
                HeaderView.class);

        bind(new TypeLiteral<UiHandlersStrategy<HeaderUiHandlers>>() {})
                .to(new TypeLiteral<ProviderUiHandlersStrategy<HeaderUiHandlers>>() {});
        bind(HeaderUiHandlers.class).to(HeaderPresenter.class);
    }

}
