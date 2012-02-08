package com.arcbees.gaestudio.client.application;

import com.arcbees.gaestudio.client.application.header.HeaderModule;
import com.arcbees.gaestudio.client.application.profiler.ProfilerModule;
import com.arcbees.gaestudio.client.application.visualizer.VisualizerModule;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ApplicationModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        install(new HeaderModule());
        install(new ProfilerModule());
        install(new VisualizerModule());

        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
                ApplicationPresenter.MyProxy.class);
    }

}
