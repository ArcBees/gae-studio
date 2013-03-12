/*
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

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
}
