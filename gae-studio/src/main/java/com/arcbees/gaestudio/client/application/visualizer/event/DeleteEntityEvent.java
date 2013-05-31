/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.event;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class DeleteEntityEvent extends GwtEvent<DeleteEntityEvent.DeleteEntityHandler> {
    private ParsedEntity parsedEntity;

    protected DeleteEntityEvent() {
        // Possibly for serialization.
    }

    public DeleteEntityEvent(ParsedEntity parsedEntity) {
        this.parsedEntity = parsedEntity;
    }

    public static void fire(HasHandlers source, ParsedEntity parsedEntity) {
        DeleteEntityEvent eventInstance = new DeleteEntityEvent(parsedEntity);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, DeleteEntityEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    public interface HasDeleteEntityHandlers extends HasHandlers {
        HandlerRegistration addDeleteEntityHandler(DeleteEntityHandler handler);
    }

    public interface DeleteEntityHandler extends EventHandler {
        public void onDeleteEntity(DeleteEntityEvent event);
    }

    private static final Type<DeleteEntityHandler> TYPE = new Type<DeleteEntityHandler>();

    public static Type<DeleteEntityHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<DeleteEntityHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DeleteEntityHandler handler) {
        handler.onDeleteEntity(this);
    }

    public ParsedEntity getParsedEntity() {
        return parsedEntity;
    }
}
