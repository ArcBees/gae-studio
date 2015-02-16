/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
