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

import java.util.Set;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class EditEntitiesEvent extends GwtEvent<EditEntitiesEvent.EntitiesSelectedHandler> {
    public interface EntitiesSelectedHandler extends EventHandler {
        void onEditEntitiesSelected(EditEntitiesEvent event);
    }

    private static final Type<EntitiesSelectedHandler> TYPE = new Type<>();

    private Set<ParsedEntity> parsedEntities;

    private EditEntitiesEvent(Set<ParsedEntity> parsedEntities) {
        this.parsedEntities = parsedEntities;
    }

    public static Type<EntitiesSelectedHandler> getType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, Set<ParsedEntity> parsedEntity) {
        EditEntitiesEvent eventInstance = new EditEntitiesEvent(parsedEntity);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, EditEntitiesEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    public Set<ParsedEntity> getParsedEntities() {
        return parsedEntities;
    }

    @Override
    public Type<EntitiesSelectedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EntitiesSelectedHandler handler) {
        handler.onEditEntitiesSelected(this);
    }
}
