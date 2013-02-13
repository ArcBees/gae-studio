package com.arcbees.gaestudio.client.application.profiler.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.web.bindery.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RecordingStateChangedEvent other = (RecordingStateChangedEvent) obj;
        if (starting != other.starting)
            return false;
        if (currentRecordId == null) {
            if (other.currentRecordId != null)
                return false;
        } else if (!currentRecordId.equals(other.currentRecordId))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 23;
        hashCode = (hashCode * 37) + new Boolean(starting).hashCode();
        hashCode = (hashCode * 37) + (currentRecordId == null ? 1 : currentRecordId.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        return "RecordingStateChangedEvent[" + starting + "," + currentRecordId + "]";
    }
}
