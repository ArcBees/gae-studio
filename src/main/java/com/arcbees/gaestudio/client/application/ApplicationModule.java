package com.arcbees.gaestudio.client.application;

import com.arcbees.gaestudio.client.application.profiler.ProfilerModule;
import com.arcbees.gaestudio.client.application.ui.UiFactory;
import com.arcbees.gaestudio.client.application.visualizer.VisualizerModule;
import com.arcbees.gaestudio.client.application.widget.HeaderModule;
import com.arcbees.gaestudio.client.application.widget.message.MessagesModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ApplicationModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        install(new HeaderModule());
        install(new ProfilerModule());
        install(new VisualizerModule());
        install(new MessagesModule());
        install(new GinFactoryModuleBuilder().build(UiFactory.class));

        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
                ApplicationPresenter.MyProxy.class);
    }

}
