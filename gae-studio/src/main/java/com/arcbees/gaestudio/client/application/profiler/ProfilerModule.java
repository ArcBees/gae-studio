package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.core.client.mvp.uihandlers.ProviderUiHandlersStrategy;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.shared.formatters.ObjectifyRecordFormatter;
import com.arcbees.gaestudio.shared.formatters.RecordFormatter;
import com.google.inject.TypeLiteral;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ProfilerModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bind(RecordFormatter.class).to(ObjectifyRecordFormatter.class);

        bind(new TypeLiteral<UiHandlersStrategy<RequestUiHandlers>>() {})
                .to(new TypeLiteral<ProviderUiHandlersStrategy<RequestUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<StatementUiHandlers>>() {})
                .to(new TypeLiteral<ProviderUiHandlersStrategy<StatementUiHandlers>>() {});
        
        bindSingletonPresenterWidget(DetailsPresenter.class, DetailsPresenter.MyView.class,
                DetailsView.class);
        bindSingletonPresenterWidget(RequestPresenter.class, RequestPresenter.MyView.class,
                RequestView.class);
        bindSingletonPresenterWidget(StatementPresenter.class, StatementPresenter.MyView.class,
                StatementView.class);
        bindSingletonPresenterWidget(StatisticsPresenter.class, StatisticsPresenter.MyView.class,
                StatisticsView.class);
        
        bind(RequestUiHandlers.class).to(RequestPresenter.class);
        bind(StatementUiHandlers.class).to(StatementPresenter.class);
        
        bindPresenter(ProfilerPresenter.class, ProfilerPresenter.MyView.class,
                ProfilerView.class, ProfilerPresenter.MyProxy.class);
    }

}
