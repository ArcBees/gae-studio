/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.channel;

import javax.inject.Inject;

import com.arcbees.gaestudio.shared.channel.Message;
import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.gson.Gson;

public class ChannelMessageSenderImpl implements ChannelMessageSender {
    private final Gson gson;

    @Inject
    ChannelMessageSenderImpl(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void sendMessage(String clientId, Message message) {
        String json = gson.toJson(message);
        ChannelService channelService = ChannelServiceFactory.getChannelService();
        ChannelMessage channelMessage = new ChannelMessage(clientId, json);
        channelService.sendMessage(channelMessage);
    }
}
