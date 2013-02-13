package com.arcbees.gaestudio.client.application.visualizer.event;

import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class EntitySavedEvent extends GwtEvent<EntitySavedEvent.EntitySavedHandler> {
    private EntityDto entityDTO;

    protected EntitySavedEvent() {
        // Possibly for serialization.
    }

    public EntitySavedEvent(com.arcbees.gaestudio.shared.dto.entity.EntityDto entityDTO) {
        this.entityDTO = entityDTO;
    }

    public static void fire(HasHandlers source, com.arcbees.gaestudio.shared.dto.entity.EntityDto entityDTO) {
        EntitySavedEvent eventInstance = new EntitySavedEvent(entityDTO);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, EntitySavedEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    public interface HasEntitySavedHandlers extends HasHandlers {
        HandlerRegistration addEntitySavedHandler(EntitySavedHandler handler);
    }

    public interface EntitySavedHandler extends EventHandler {
        public void onEntitySaved(EntitySavedEvent event);
    }

    private static final Type<EntitySavedHandler> TYPE = new Type<EntitySavedHandler>();

    public static Type<EntitySavedHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<EntitySavedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EntitySavedHandler handler) {
        handler.onEntitySaved(this);
    }

    public com.arcbees.gaestudio.shared.dto.entity.EntityDto getEntityDTO() {
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
        EntitySavedEvent other = (EntitySavedEvent) obj;
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
        return "EntitySavedEvent[" + entityDTO + "]";
    }
}
