/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.event;

import java.util.Set;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class EditEntitiesEvent extends GwtEvent<EditEntitiesEvent.EntitySelectedHandler> {
    public interface EntitySelectedHandler extends EventHandler {
        public void onEditEntitiesSelected(EditEntitiesEvent event);
    }

    public static void fire(HasHandlers source, Set<ParsedEntity> parsedEntity) {
        EditEntitiesEvent eventInstance = new EditEntitiesEvent(parsedEntity);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, EditEntitiesEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    private static final Type<EntitySelectedHandler> TYPE = new Type<>();

    public static Type<EntitySelectedHandler> getType() {
        return TYPE;
    }

    private Set<ParsedEntity> parsedEntities;

    public EditEntitiesEvent(Set<ParsedEntity> parsedEntities) {
        this.parsedEntities = parsedEntities;
    }

    public Set<ParsedEntity> getParsedEntities() {
        return parsedEntities;
    }

    @Override
    public Type<EntitySelectedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EntitySelectedHandler handler) {
        handler.onEditEntitiesSelected(this);
    }
}
