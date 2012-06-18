package com.arcbees.gaestudio.client.application.visualizer;

import com.arcbees.core.client.mvp.uihandlers.ProviderUiHandlersStrategy;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.application.visualizer.widget.EntityDetailsPresenter;
import com.arcbees.gaestudio.client.application.visualizer.widget.EntityDetailsUiHandlers;
import com.arcbees.gaestudio.client.application.visualizer.widget.EntityDetailsView;
import com.arcbees.gaestudio.client.application.visualizer.widget.EntityListPresenter;
import com.arcbees.gaestudio.client.application.visualizer.widget.EntityListUiHandlers;
import com.arcbees.gaestudio.client.application.visualizer.widget.EntityListView;
import com.arcbees.gaestudio.client.application.visualizer.widget.KindListPresenter;
import com.arcbees.gaestudio.client.application.visualizer.widget.KindListUiHandlers;
import com.arcbees.gaestudio.client.application.visualizer.widget.KindListView;
import com.arcbees.gaestudio.client.application.visualizer.widget.VisualizerToolbarPresenter;
import com.arcbees.gaestudio.client.application.visualizer.widget.VisualizerToolbarUiHandlers;
import com.arcbees.gaestudio.client.application.visualizer.widget.VisualizerToolbarView;
import com.google.inject.TypeLiteral;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class VisualizerModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bindPresenter(VisualizerPresenter.class, VisualizerPresenter.MyView.class,
                VisualizerView.class, VisualizerPresenter.MyProxy.class);

        bindSingletonPresenterWidget(EntityListPresenter.class, EntityListPresenter.MyView.class,
                EntityListView.class);
        bind(EntityListUiHandlers.class).to(EntityListPresenter.class);
        bind(new TypeLiteral<UiHandlersStrategy<EntityListUiHandlers>>() {})
                .to(new TypeLiteral<ProviderUiHandlersStrategy<EntityListUiHandlers>>() {});

        bindSingletonPresenterWidget(VisualizerToolbarPresenter.class, VisualizerToolbarPresenter.MyView.class,
                VisualizerToolbarView.class);
        bind(VisualizerToolbarUiHandlers.class).to(VisualizerToolbarPresenter.class);
        bind(new TypeLiteral<UiHandlersStrategy<VisualizerToolbarUiHandlers>>() {}).to(
                new TypeLiteral<ProviderUiHandlersStrategy<VisualizerToolbarUiHandlers>>() {});

        bindSingletonPresenterWidget(KindListPresenter.class, KindListPresenter.MyView.class,
                KindListView.class);
        bind(KindListUiHandlers.class).to(KindListPresenter.class);
        bind(new TypeLiteral<UiHandlersStrategy<KindListUiHandlers>>() {}).to(
                new TypeLiteral<ProviderUiHandlersStrategy<KindListUiHandlers>>() {});

        bind(EntityDetailsPresenter.class).asEagerSingleton();
        bind(EntityDetailsPresenter.MyView.class).to(EntityDetailsView.class);
        bind(EntityDetailsUiHandlers.class).to(EntityDetailsPresenter.class);
        bind(new TypeLiteral<UiHandlersStrategy<EntityDetailsUiHandlers>>() {})
                .to(new TypeLiteral<ProviderUiHandlersStrategy<EntityDetailsUiHandlers>>() {});
    }

}
