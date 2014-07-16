/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.channel;

import java.util.Map;

import javax.inject.Inject;

import com.arcbees.gaestudio.shared.channel.Topic;
import com.google.common.collect.ImmutableMap;
import com.google.gwt.json.client.JSONValue;

public class MessageHandlers {
    private final Map<Topic, MessageHandler> HANDLER_MAP;

    @Inject
    MessageHandlers(ImportCompletedMessageHandler importCompletedMessageHandler) {
        HANDLER_MAP = ImmutableMap.<Topic, MessageHandler>builder()
                .put(importCompletedMessageHandler.getTopic(), importCompletedMessageHandler)
                .build();
    }

    public void handle(Topic topic, JSONValue payload) {
        MessageHandler messageHandler = HANDLER_MAP.get(topic);
        messageHandler.handleMessage(payload);
    }
}
