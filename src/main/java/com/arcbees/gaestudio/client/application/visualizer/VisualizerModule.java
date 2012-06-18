package com.arcbees.gaestudio.client.application.visualizer;

import com.arcbees.core.client.mvp.uihandlers.ProviderUiHandlersStrategy;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.application.visualizer.widget.EntityDetailsPresenter;
import com.arcbees.gaestudio.client.application.visualizer.widget.EntityDetailsUiHandlers;
import com.arcbees.gaestudio.client.application.visualizer.widget.EntityDetailsView;
import com.arcbees.gaestudio.client.application.visualizer.widget.EntityListPresenter;
import com.arcbees.gaestudio.client.application.visualizer.widget.EntityListUiHandlers;
import com.arcbees.gaestudio.client.application.visualizer.widget.EntityListView;
import com.arcbees.gaestudio.client.application.visualizer.widget.WidgetModule;
import com.google.inject.TypeLiteral;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class VisualizerModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        install(new WidgetModule());

        bind(new TypeLiteral<UiHandlersStrategy<EntityListUiHandlers>>() {})
                .to(new TypeLiteral<ProviderUiHandlersStrategy<EntityListUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<EntityDetailsUiHandlers>>() {})
                .to(new TypeLiteral<ProviderUiHandlersStrategy<EntityDetailsUiHandlers>>() {});

        bindSingletonPresenterWidget(EntityListPresenter.class, EntityListPresenter.MyView.class,
                EntityListView.class);
        bind(EntityDetailsPresenter.class).asEagerSingleton();
        bind(EntityDetailsPresenter.MyView.class).to(EntityDetailsView.class);

        bind(EntityListUiHandlers.class).to(EntityListPresenter.class);
        bind(EntityDetailsUiHandlers.class).to(EntityDetailsPresenter.class);

        bindPresenter(VisualizerPresenter.class, VisualizerPresenter.MyView.class,
                VisualizerView.class, VisualizerPresenter.MyProxy.class);
    }

}
