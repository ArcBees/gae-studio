/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.channel;

import java.util.UUID;

import javax.inject.Provider;
import javax.servlet.http.HttpSession;

import com.arcbees.gaestudio.shared.channel.Constants;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class ChannelModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ChannelMessageSender.class).to(ChannelMessageSenderImpl.class);
    }

    @Provides
    @ClientId
    String getClientId(Provider<HttpSession> httpSessionProvider) {
        HttpSession httpSession = httpSessionProvider.get();

        Object clientId = httpSession.getAttribute(Constants.CLIENT_ID);

        if (clientId == null) {
            clientId = UUID.randomUUID();
            httpSession.setAttribute(Constants.CLIENT_ID, clientId);
        }

        return clientId.toString();
    }
}
