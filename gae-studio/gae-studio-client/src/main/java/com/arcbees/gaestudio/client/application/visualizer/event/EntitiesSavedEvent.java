/**
 * Copyright 2015 ArcBees Inc.
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

import java.util.List;

import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class EntitiesSavedEvent extends GwtEvent<EntitiesSavedEvent.EntitiesSavedHandler> {
    public interface EntitiesSavedHandler extends EventHandler {
        void onEntitiesSaved(EntitiesSavedEvent event);
    }

    private static final Type<EntitiesSavedHandler> TYPE = new Type<>();

    private final List<EntityDto> entities;

    private EntitiesSavedEvent(List<EntityDto> entities) {
        this.entities = entities;
    }

    public static void fire(HasHandlers source, List<EntityDto> entities) {
        source.fireEvent(new EntitiesSavedEvent(entities));
    }

    public static Type<EntitiesSavedHandler> getType() {
        return TYPE;
    }

    public List<EntityDto> getEntities() {
        return entities;
    }

    @Override
    public Type<EntitiesSavedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EntitiesSavedHandler handler) {
        handler.onEntitiesSaved(this);
    }
}
