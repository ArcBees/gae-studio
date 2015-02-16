/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.channel;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.event.ImportCompletedEvent;
import com.arcbees.gaestudio.client.application.widget.ajax.LoadingEvent;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.client.resources.AppMessages;
import com.arcbees.gaestudio.shared.Constants;
import com.arcbees.gaestudio.shared.channel.Topic;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.json.client.JSONValue;
import com.google.web.bindery.event.shared.EventBus;

public class ExportCompletedMessageHandler implements MessageHandler, HasHandlers {
    private final EventBus eventBus;
    private final AppMessages appMessages;

    @Inject
    ExportCompletedMessageHandler(
            EventBus eventBus,
            AppMessages appMessages) {
        this.eventBus = eventBus;
        this.appMessages = appMessages;
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        eventBus.fireEvent(event);
    }

    @Override
    public void handleMessage(JSONValue payload) {
        DisplayMessageEvent.fire(this,
                new Message(appMessages.exportTooLarge(Constants.FREE_IMPORT_EXPORT_QUOTA), MessageStyle.SUCCESS));
        ImportCompletedEvent.fire(this);
        LoadingEvent.fire(this, LoadingEvent.Action.END);
    }

    @Override
    public Topic getTopic() {
        return Topic.EXPORT_COMPLETED;
    }
}
