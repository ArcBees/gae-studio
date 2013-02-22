package com.arcbees.gaestudio.client.application.visualizer.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.web.bindery.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class EntityPageLoadedEvent extends GwtEvent<EntityPageLoadedEvent.EntityPageLoadedHandler> {
    public EntityPageLoadedEvent() {
        // Possibly for serialization.
    }

    public static void fire(HasHandlers source) {
        EntityPageLoadedEvent eventInstance = new EntityPageLoadedEvent();
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, EntityPageLoadedEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    public interface HasEntityPageLoadedHandlers extends HasHandlers {
        HandlerRegistration addEntityPageLoadedHandler(EntityPageLoadedHandler handler);
    }

    public interface EntityPageLoadedHandler extends EventHandler {
        public void onEntityPageLoaded(EntityPageLoadedEvent event);
    }

    private static final Type<EntityPageLoadedHandler> TYPE = new Type<EntityPageLoadedHandler>();

    public static Type<EntityPageLoadedHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<EntityPageLoadedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EntityPageLoadedHandler handler) {
        handler.onEntityPageLoaded(this);
    }
}
