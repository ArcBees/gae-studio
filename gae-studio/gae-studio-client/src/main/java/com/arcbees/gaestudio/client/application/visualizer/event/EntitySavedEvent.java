/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.event;

import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class EntitySavedEvent extends GwtEvent<EntitySavedEvent.EntitySavedHandler> {
    private EntityDto entityDto;

    public EntitySavedEvent(EntityDto entityDto) {
        this.entityDto = entityDto;
    }

    public static void fire(HasHandlers source, EntityDto entityDTO) {
        EntitySavedEvent eventInstance = new EntitySavedEvent(entityDTO);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, EntitySavedEvent eventInstance) {
        source.fireEvent(eventInstance);
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

    public EntityDto getEntityDto() {
        return entityDto;
    }
}
