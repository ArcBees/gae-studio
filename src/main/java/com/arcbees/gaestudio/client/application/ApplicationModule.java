package com.arcbees.gaestudio.client.application;

import com.arcbees.gaestudio.client.application.profiler.ProfilerModule;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ApplicationModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        install(new ProfilerModule());

        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
                ApplicationPresenter.MyProxy.class);
    }

}
