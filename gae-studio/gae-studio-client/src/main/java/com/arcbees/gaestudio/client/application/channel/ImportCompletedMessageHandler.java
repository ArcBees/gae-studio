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
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.shared.channel.Topic;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.json.client.JSONValue;
import com.google.web.bindery.event.shared.EventBus;

public class ImportCompletedMessageHandler implements MessageHandler, HasHandlers {
    private final AppConstants appConstants;
    private final EventBus eventBus;

    @Inject
    ImportCompletedMessageHandler(AppConstants appConstants,
                                  EventBus eventBus) {
        this.appConstants = appConstants;
        this.eventBus = eventBus;
    }

    @Override
    public void handleMessage(JSONValue payload) {
        Message displayMessage = new Message(appConstants.importSuccess(), MessageStyle.SUCCESS);
        DisplayMessageEvent.fire(this, displayMessage);
        ImportCompletedEvent.fire(this);
        LoadingEvent.fire(this, LoadingEvent.Action.END);
    }

    @Override
    public Topic getTopic() {
        return Topic.IMPORT_COMPLETED;
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        eventBus.fireEvent(event);
    }
}
