package com.arcbees.gaestudio.client.application.visualizer.event;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class EntitySelectedEvent extends GwtEvent<EntitySelectedEvent.EntitySelectedHandler> {
    private ParsedEntity parsedEntity;

    protected EntitySelectedEvent() {
        // Possibly for serialization.
    }

    public EntitySelectedEvent(com.arcbees.gaestudio.client.application.visualizer.ParsedEntity parsedEntity) {
        this.parsedEntity = parsedEntity;
    }

    public static void fire(HasHandlers source,
            com.arcbees.gaestudio.client.application.visualizer.ParsedEntity parsedEntity) {
        EntitySelectedEvent eventInstance = new EntitySelectedEvent(parsedEntity);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, EntitySelectedEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    public interface HasEntitySelectedHandlers extends HasHandlers {
        HandlerRegistration addEntitySelectedHandler(EntitySelectedHandler handler);
    }

    public interface EntitySelectedHandler extends EventHandler {
        public void onEntitySelected(EntitySelectedEvent event);
    }

    private static final Type<EntitySelectedHandler> TYPE = new Type<EntitySelectedHandler>();

    public static Type<EntitySelectedHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<EntitySelectedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EntitySelectedHandler handler) {
        handler.onEntitySelected(this);
    }

    public com.arcbees.gaestudio.client.application.visualizer.ParsedEntity getParsedEntity() {
        return parsedEntity;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EntitySelectedEvent other = (EntitySelectedEvent) obj;
        if (parsedEntity == null) {
            if (other.parsedEntity != null)
                return false;
        } else if (!parsedEntity.equals(other.parsedEntity))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 23;
        hashCode = (hashCode * 37) + (parsedEntity == null ? 1 : parsedEntity.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        return "EntitySelectedEvent[" + parsedEntity + "]";
    }
}
