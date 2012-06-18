package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.core.client.mvp.uihandlers.ProviderUiHandlersStrategy;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.application.visualizer.widget.kind.KindListPresenter;
import com.arcbees.gaestudio.client.application.visualizer.widget.kind.KindListUiHandlers;
import com.arcbees.gaestudio.client.application.visualizer.widget.kind.KindListView;
import com.google.inject.TypeLiteral;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class WidgetModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bindSingletonPresenterWidget(VisualizerToolbarPresenter.class, VisualizerToolbarPresenter.MyView.class,
                VisualizerToolbarView.class);
        bindSingletonPresenterWidget(KindListPresenter.class, KindListPresenter.MyView.class,
                KindListView.class);

        bind(VisualizerToolbarUiHandlers.class).to(VisualizerToolbarPresenter.class);
        bind(KindListUiHandlers.class).to(KindListPresenter.class);

        bind(new TypeLiteral<UiHandlersStrategy<VisualizerToolbarUiHandlers>>() {}).to(
                new TypeLiteral<ProviderUiHandlersStrategy<VisualizerToolbarUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<KindListUiHandlers>>() {}).to(
                new TypeLiteral<ProviderUiHandlersStrategy<KindListUiHandlers>>() {});


    }

}
