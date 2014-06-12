/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.widget.message;

import javax.inject.Singleton;

import com.arcbees.gaestudio.client.resources.MessageResources;
import com.arcbees.gaestudio.client.ui.MessageWidgetFactory;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class MessagesModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new GinFactoryModuleBuilder().build(MessageWidgetFactory.class));

        bind(MessageResources.class).in(Singleton.class);

        bindSingletonPresenterWidget(MessagesPresenter.class, MessagesPresenter.MyView.class, MessagesView.class);
    }
}
