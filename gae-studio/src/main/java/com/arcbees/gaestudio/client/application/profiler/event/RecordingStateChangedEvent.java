package com.arcbees.gaestudio.client.application.profiler.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class RecordingStateChangedEvent extends GwtEvent<RecordingStateChangedEvent.RecordingStateChangedHandler> {
    private boolean starting;
    private Long currentRecordId;

    protected RecordingStateChangedEvent() {
        // Possibly for serialization.
    }

    public RecordingStateChangedEvent(boolean starting, Long currentRecordId) {
        this.starting = starting;
        this.currentRecordId = currentRecordId;
    }

    public static void fire(HasHandlers source, boolean starting, Long currentRecordId) {
        RecordingStateChangedEvent eventInstance = new RecordingStateChangedEvent(starting, currentRecordId);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, RecordingStateChangedEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    public interface HasRecordingStateChangedHandlers extends HasHandlers {
        HandlerRegistration addRecordingStateChangedHandler(RecordingStateChangedHandler handler);
    }

    public interface RecordingStateChangedHandler extends EventHandler {
        public void onRecordingStateChanged(RecordingStateChangedEvent event);
    }

    private static final Type<RecordingStateChangedHandler> TYPE = new Type<RecordingStateChangedHandler>();

    public static Type<RecordingStateChangedHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<RecordingStateChangedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(RecordingStateChangedHandler handler) {
        handler.onRecordingStateChanged(this);
    }

    public boolean isStarting() {
        return starting;
    }

    public Long getCurrentRecordId() {
        return currentRecordId;
    }
}
