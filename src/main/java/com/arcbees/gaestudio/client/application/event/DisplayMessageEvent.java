package com.arcbees.gaestudio.client.application.event;

import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class DisplayMessageEvent extends GwtEvent<DisplayMessageEvent.DisplayMessageHandler> {
    private Message message;

    protected DisplayMessageEvent() {
        // Possibly for serialization.
    }

    public DisplayMessageEvent(Message message) {
        this.message = message;
    }

    public static void fire(HasHandlers source, Message message) {
        DisplayMessageEvent eventInstance = new DisplayMessageEvent(message);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, DisplayMessageEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    public interface HasDisplayMessageHandlers extends HasHandlers {
        HandlerRegistration addDisplayMessageHandler(DisplayMessageHandler handler);
    }

    public interface DisplayMessageHandler extends EventHandler {
        public void onDisplayMessage(DisplayMessageEvent event);
    }

    private static final Type<DisplayMessageHandler> TYPE = new Type<DisplayMessageHandler>();

    public static Type<DisplayMessageHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<DisplayMessageHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DisplayMessageHandler handler) {
        handler.onDisplayMessage(this);
    }

    public Message getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DisplayMessageEvent other = (DisplayMessageEvent) obj;
        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 23;
        hashCode = (hashCode * 37) + (message == null ? 1 : message.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        return "DisplayMessageEvent[" + message + "]";
    }
}
