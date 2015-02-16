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
