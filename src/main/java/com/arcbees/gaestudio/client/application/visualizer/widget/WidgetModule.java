package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.core.client.mvp.uihandlers.ProviderUiHandlersStrategy;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.google.inject.TypeLiteral;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class WidgetModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bindSingletonPresenterWidget(VisualizerToolbarPresenter.class, VisualizerToolbarPresenter.MyView.class,
                VisualizerToolbarView.class);
        bind(new TypeLiteral<UiHandlersStrategy<VisualizerToolbarUiHandlers>>() {
        })
                .to(new TypeLiteral<ProviderUiHandlersStrategy<VisualizerToolbarUiHandlers>>() {
        });
        bind(VisualizerToolbarUiHandlers.class).to(VisualizerToolbarPresenter.class);
    }

}
