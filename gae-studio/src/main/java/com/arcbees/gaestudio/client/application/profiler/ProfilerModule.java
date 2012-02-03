package com.arcbees.gaestudio.client.application.profiler;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ProfilerModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bindPresenterWidget(DetailsPresenter.class, DetailsPresenter.MyView.class, DetailsView.class);
        bindPresenterWidget(RequestPresenter.class, RequestPresenter.MyView.class, RequestView.class);
        bindPresenterWidget(StatementPresenter.class, StatementPresenter.MyView.class, StatementView.class);
        bindPresenterWidget(StatisticsPresenter.class, StatisticsPresenter.MyView.class, StatisticsView.class);
        
        bindPresenter(ProfilerPresenter.class, ProfilerPresenter.MyView.class,
                ProfilerView.class, ProfilerPresenter.MyProxy.class);
    }

}
