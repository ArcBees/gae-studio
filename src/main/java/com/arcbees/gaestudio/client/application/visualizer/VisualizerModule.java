package com.arcbees.gaestudio.client.application.visualizer;

import com.arcbees.core.client.mvp.uihandlers.ProviderUiHandlersStrategy;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.application.visualizer.entitydetails.EntityDetailsPresenter;
import com.arcbees.gaestudio.client.application.visualizer.entitydetails.EntityDetailsUiHandlers;
import com.arcbees.gaestudio.client.application.visualizer.entitydetails.EntityDetailsView;
import com.arcbees.gaestudio.client.application.visualizer.entitylist.EntityListPresenter;
import com.arcbees.gaestudio.client.application.visualizer.entitylist.EntityListUiHandlers;
import com.arcbees.gaestudio.client.application.visualizer.entitylist.EntityListView;
import com.arcbees.gaestudio.client.application.visualizer.kind.KindListPresenter;
import com.arcbees.gaestudio.client.application.visualizer.kind.KindListUiHandlers;
import com.arcbees.gaestudio.client.application.visualizer.kind.KindListView;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.TypeLiteral;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class VisualizerModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        install(new GinFactoryModuleBuilder().build(VisualizerLabelFactory.class));
        bind(new TypeLiteral<UiHandlersStrategy<KindListUiHandlers>>() {})
                .to(new TypeLiteral<ProviderUiHandlersStrategy<KindListUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<EntityListUiHandlers>>() {})
                .to(new TypeLiteral<ProviderUiHandlersStrategy<EntityListUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<EntityDetailsUiHandlers>>() {})
                .to(new TypeLiteral<ProviderUiHandlersStrategy<EntityDetailsUiHandlers>>() {});

        bindSingletonPresenterWidget(KindListPresenter.class, KindListPresenter.MyView.class,
                KindListView.class);
        bindSingletonPresenterWidget(EntityListPresenter.class, EntityListPresenter.MyView.class,
                EntityListView.class);
        bindSingletonPresenterWidget(EntityDetailsPresenter.class, EntityDetailsPresenter.MyView.class,
                EntityDetailsView.class);

        bind(KindListUiHandlers.class).to(KindListPresenter.class);
        bind(EntityListUiHandlers.class).to(EntityListPresenter.class);
        bind(EntityDetailsUiHandlers.class).to(EntityDetailsPresenter.class);

        bindPresenter(VisualizerPresenter.class, VisualizerPresenter.MyView.class,
                VisualizerView.class, VisualizerPresenter.MyProxy.class);
    }

}
