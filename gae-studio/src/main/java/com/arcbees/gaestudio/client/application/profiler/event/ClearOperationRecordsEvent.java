package com.arcbees.gaestudio.client.application.profiler.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.web.bindery.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class ClearOperationRecordsEvent extends GwtEvent<ClearOperationRecordsEvent.ClearOperationRecordsHandler> {
    public ClearOperationRecordsEvent() {
        // Possibly for serialization.
    }

    public static void fire(HasHandlers source) {
        ClearOperationRecordsEvent eventInstance = new ClearOperationRecordsEvent();
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, ClearOperationRecordsEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    public interface HasClearOperationRecordsHandlers extends HasHandlers {
        HandlerRegistration addClearOperationRecordsHandler(ClearOperationRecordsHandler handler);
    }

    public interface ClearOperationRecordsHandler extends EventHandler {
        public void onClearOperationRecords(ClearOperationRecordsEvent event);
    }

    private static final Type<ClearOperationRecordsHandler> TYPE = new Type<ClearOperationRecordsHandler>();

    public static Type<ClearOperationRecordsHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<ClearOperationRecordsHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ClearOperationRecordsHandler handler) {
        handler.onClearOperationRecords(this);
    }
}
