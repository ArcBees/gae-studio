package com.arcbees.gaestudio.client.application.profiler;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ProfilerModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bindSingletonPresenterWidget(DetailsPresenter.class, DetailsPresenter.MyView.class,
                DetailsView.class);
        bindSingletonPresenterWidget(RequestPresenter.class, RequestPresenter.MyView.class,
                RequestView.class);
        bindSingletonPresenterWidget(StatementPresenter.class, StatementPresenter.MyView.class,
                StatementView.class);
        bindSingletonPresenterWidget(StatisticsPresenter.class, StatisticsPresenter.MyView.class,
                StatisticsView.class);
        
        bindPresenter(ProfilerPresenter.class, ProfilerPresenter.MyView.class,
                ProfilerView.class, ProfilerPresenter.MyProxy.class);
    }

}
