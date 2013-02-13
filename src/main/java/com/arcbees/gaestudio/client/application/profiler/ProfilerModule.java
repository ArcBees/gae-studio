package com.arcbees.gaestudio.client.application.profiler;

import javax.inject.Singleton;

import com.arcbees.gaestudio.client.application.profiler.widget.DetailsPresenter;
import com.arcbees.gaestudio.client.application.profiler.widget.DetailsView;
import com.arcbees.gaestudio.client.application.profiler.widget.ProfilerToolbarPresenter;
import com.arcbees.gaestudio.client.application.profiler.widget.ProfilerToolbarUiHandlers;
import com.arcbees.gaestudio.client.application.profiler.widget.ProfilerToolbarView;
import com.arcbees.gaestudio.client.application.profiler.widget.StatementPresenter;
import com.arcbees.gaestudio.client.application.profiler.widget.StatementUiHandlers;
import com.arcbees.gaestudio.client.application.profiler.widget.StatementView;
import com.arcbees.gaestudio.client.application.profiler.widget.StatisticsPresenter;
import com.arcbees.gaestudio.client.application.profiler.widget.StatisticsView;
import com.arcbees.gaestudio.client.application.profiler.widget.filter.FilterModule;
import com.arcbees.gaestudio.client.formatters.ObjectifyRecordFormatter;
import com.arcbees.gaestudio.client.formatters.RecordFormatter;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ProfilerModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        install(new FilterModule());

        bind(RecordFormatter.class).to(ObjectifyRecordFormatter.class).in(Singleton.class);

        bindSingletonPresenterWidget(DetailsPresenter.class, DetailsPresenter.MyView.class,
                DetailsView.class);
        bindSingletonPresenterWidget(StatementPresenter.class, StatementPresenter.MyView.class,
                StatementView.class);
        bindSingletonPresenterWidget(StatisticsPresenter.class, StatisticsPresenter.MyView.class,
                StatisticsView.class);
        bindSingletonPresenterWidget(ProfilerToolbarPresenter.class, ProfilerToolbarPresenter.MyView.class,
                ProfilerToolbarView.class);

        bind(StatementUiHandlers.class).to(StatementPresenter.class);
        bind(ProfilerToolbarUiHandlers.class).to(ProfilerToolbarPresenter.class);

        bindPresenter(ProfilerPresenter.class, ProfilerPresenter.MyView.class,
                ProfilerView.class, ProfilerPresenter.MyProxy.class);
    }

}
