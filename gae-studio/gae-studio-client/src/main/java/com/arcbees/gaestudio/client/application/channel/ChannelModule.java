/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.channel;

import javax.inject.Singleton;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.multibindings.GinMultibinder;

public class ChannelModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(ChannelHandler.class).in(Singleton.class);

        GinMultibinder<MessageHandler> multibinder = GinMultibinder.newSetBinder(binder(), MessageHandler.class);
        multibinder.addBinding().to(ImportCompletedMessageHandler.class);
        multibinder.addBinding().to(ImportTooLargeMessageHandler.class);
        multibinder.addBinding().to(ExportCompletedMessageHandler.class);
    }
}
