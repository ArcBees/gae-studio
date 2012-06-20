package com.arcbees.gaestudio.client.application.widget.message;

import com.arcbees.gaestudio.client.application.widget.message.ui.MessageResources;
import com.arcbees.gaestudio.client.application.widget.message.ui.MessageWidgetFactory;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

import javax.inject.Singleton;

public class MessagesModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        install(new GinFactoryModuleBuilder().build(MessageWidgetFactory.class));

        bind(MessageResources.class).in(Singleton.class);

        bindSingletonPresenterWidget(MessagesPresenter.class, MessagesPresenter.MyView.class, MessagesView.class);
    }

}
