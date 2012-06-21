package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.core.client.mvp.uihandlers.ProviderUiHandlersStrategy;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.application.profiler.widget.DetailsPresenter;
import com.arcbees.gaestudio.client.application.profiler.widget.DetailsView;
import com.arcbees.gaestudio.client.application.profiler.widget.ProfilerToolbarPresenter;
import com.arcbees.gaestudio.client.application.profiler.widget.ProfilerToolbarUiHandlers;
import com.arcbees.gaestudio.client.application.profiler.widget.ProfilerToolbarView;
import com.arcbees.gaestudio.client.application.profiler.widget.RequestPresenter;
import com.arcbees.gaestudio.client.application.profiler.widget.RequestUiHandlers;
import com.arcbees.gaestudio.client.application.profiler.widget.RequestView;
import com.arcbees.gaestudio.client.application.profiler.widget.StatementPresenter;
import com.arcbees.gaestudio.client.application.profiler.widget.StatementUiHandlers;
import com.arcbees.gaestudio.client.application.profiler.widget.StatementView;
import com.arcbees.gaestudio.client.application.profiler.widget.StatisticsPresenter;
import com.arcbees.gaestudio.client.application.profiler.widget.StatisticsView;
import com.arcbees.gaestudio.shared.formatters.ObjectifyRecordFormatter;
import com.arcbees.gaestudio.shared.formatters.RecordFormatter;
import com.google.inject.TypeLiteral;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

import javax.inject.Singleton;

public class ProfilerModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bind(RecordFormatter.class).to(ObjectifyRecordFormatter.class).in(Singleton.class);

        bind(new TypeLiteral<UiHandlersStrategy<RequestUiHandlers>>() {})
                .to(new TypeLiteral<ProviderUiHandlersStrategy<RequestUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<StatementUiHandlers>>() {})
                .to(new TypeLiteral<ProviderUiHandlersStrategy<StatementUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<ProfilerToolbarUiHandlers>>(){})
                .to(new TypeLiteral<ProviderUiHandlersStrategy<ProfilerToolbarUiHandlers>>(){});

        bindSingletonPresenterWidget(DetailsPresenter.class, DetailsPresenter.MyView.class,
                DetailsView.class);
        bindSingletonPresenterWidget(RequestPresenter.class, RequestPresenter.MyView.class,
                RequestView.class);
        bindSingletonPresenterWidget(StatementPresenter.class, StatementPresenter.MyView.class,
                StatementView.class);
        bindSingletonPresenterWidget(StatisticsPresenter.class, StatisticsPresenter.MyView.class,
                StatisticsView.class);
        bindSingletonPresenterWidget(ProfilerToolbarPresenter.class, ProfilerToolbarPresenter.MyView.class,
                ProfilerToolbarView.class);

        bind(RequestUiHandlers.class).to(RequestPresenter.class);
        bind(StatementUiHandlers.class).to(StatementPresenter.class);
        bind(ProfilerToolbarUiHandlers.class).to(ProfilerToolbarPresenter.class);

        bindPresenter(ProfilerPresenter.class, ProfilerPresenter.MyView.class,
                ProfilerView.class, ProfilerPresenter.MyProxy.class);
    }

}
