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
import com.arcbees.gaestudio.shared.DeleteEntities;
import com.google.common.collect.Sets;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class DeleteEntitiesEvent extends GwtEvent<DeleteEntitiesEvent.DeleteEntitiesHandler> {
    public interface DeleteEntitiesHandler extends EventHandler {
        void onDeleteEntities(DeleteEntitiesEvent event);
    }

    private static final Type<DeleteEntitiesHandler> TYPE = new Type<DeleteEntitiesHandler>();

    private DeleteEntities deleteEntities;
    private Set<ParsedEntity> entities;
    private String kind;
    private String namespace;

    DeleteEntitiesEvent(
            DeleteEntities deleteEntities,
            String kind,
            String namespace) {
        this.deleteEntities = deleteEntities;
        this.kind = kind;
        this.namespace = namespace;
        this.entities = Sets.newHashSet();
    }

    DeleteEntitiesEvent(
            DeleteEntities deleteEntities,
            Set<ParsedEntity> entities) {
        this.deleteEntities = deleteEntities;
        this.entities = entities;
    }

    public static Type<DeleteEntitiesHandler> getType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, DeleteEntities deleteEntities) {
        source.fireEvent(new DeleteEntitiesEvent(deleteEntities, null, null));
    }

    public static void fire(HasHandlers source, DeleteEntities deleteEntities, String kind, String namespace) {
        source.fireEvent(new DeleteEntitiesEvent(deleteEntities, kind, namespace));
    }

    public static void fire(HasHandlers source, DeleteEntities deleteEntities, String kind) {
        source.fireEvent(new DeleteEntitiesEvent(deleteEntities, kind, null));
    }

    public static void fire(HasHandlers source, DeleteEntities deleteEntities, Set<ParsedEntity> entities) {
        source.fireEvent(new DeleteEntitiesEvent(deleteEntities, entities));
    }

    public DeleteEntities getDeleteEntities() {
        return deleteEntities;
    }

    public String getKind() {
        return kind;
    }

    public String getNamespace() {
        return namespace;
    }

    public Set<ParsedEntity> getEntities() {
        return entities;
    }

    @Override
    public Type<DeleteEntitiesHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DeleteEntitiesHandler handler) {
        handler.onDeleteEntities(this);
    }
}
