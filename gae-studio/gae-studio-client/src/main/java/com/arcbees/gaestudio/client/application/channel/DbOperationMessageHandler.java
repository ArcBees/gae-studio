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

import com.arcbees.gaestudio.client.application.event.DbOperationEvent;
import com.arcbees.gaestudio.client.application.profiler.DbOperationDeserializer;
import com.arcbees.gaestudio.shared.channel.Topic;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.json.client.JSONValue;
import com.google.web.bindery.event.shared.EventBus;

public class DbOperationMessageHandler implements MessageHandler, HasHandlers {
    private final EventBus eventBus;
    private final DbOperationDeserializer dbOperationDeserializer;

    @Inject
    DbOperationMessageHandler(
            EventBus eventBus,
            DbOperationDeserializer dbOperationDeserializer) {
        this.eventBus = eventBus;
        this.dbOperationDeserializer = dbOperationDeserializer;
    }

    @Override
    public void handleMessage(JSONValue payload) {
        DbOperationRecordDto recordDto = dbOperationDeserializer.deserialize(payload.isString().stringValue());

        DbOperationEvent.fire(this, recordDto);
    }

    @Override
    public Topic getTopic() {
        return Topic.DB_OPERATION;
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        eventBus.fireEvent(event);
    }
}
