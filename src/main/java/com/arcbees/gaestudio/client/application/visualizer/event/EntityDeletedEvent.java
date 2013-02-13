package com.arcbees.gaestudio.client.application.visualizer.event;

import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class EntityDeletedEvent extends GwtEvent<EntityDeletedEvent.EntityDeletedHandler> {
    private EntityDto entityDTO;

    protected EntityDeletedEvent() {
        // Possibly for serialization.
    }

    public EntityDeletedEvent(EntityDto entityDTO) {
        this.entityDTO = entityDTO;
    }

    public static void fire(HasHandlers source, EntityDto entityDTO) {
        EntityDeletedEvent eventInstance = new EntityDeletedEvent(entityDTO);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, EntityDeletedEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    public interface HasEntityDeletedHandlers extends HasHandlers {
        HandlerRegistration addEntityDeletedHandler(EntityDeletedHandler handler);
    }

    public interface EntityDeletedHandler extends EventHandler {
        public void onEntityDeleted(EntityDeletedEvent event);
    }

    private static final Type<EntityDeletedHandler> TYPE = new Type<EntityDeletedHandler>();

    public static Type<EntityDeletedHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<EntityDeletedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EntityDeletedHandler handler) {
        handler.onEntityDeleted(this);
    }

    public EntityDto getEntityDTO() {
        return entityDTO;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EntityDeletedEvent other = (EntityDeletedEvent) obj;
        if (entityDTO == null) {
            if (other.entityDTO != null)
                return false;
        } else if (!entityDTO.equals(other.entityDTO))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 23;
        hashCode = (hashCode * 37) + (entityDTO == null ? 1 : entityDTO.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        return "EntityDeletedEvent[" + entityDTO + "]";
    }
}
