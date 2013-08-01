/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.event;

import com.arcbees.gaestudio.shared.dispatch.DeleteEntitiesAction;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class DeleteEntitiesEvent extends GwtEvent<DeleteEntitiesEvent.DeleteEntitiesHandler> {
    public interface DeleteEntitiesHandler extends EventHandler {
        public void onDeleteEntities(DeleteEntitiesEvent event);
    }

    public interface HasDeleteEntitiesHandlers extends HasHandlers {
        HandlerRegistration addDeleteEntitiesHandler(DeleteEntitiesHandler handler);
    }

    private static final Type<DeleteEntitiesHandler> TYPE = new Type<DeleteEntitiesHandler>();

    private DeleteEntitiesAction deleteEntitiesAction;

    protected DeleteEntitiesEvent() {
        // Possibly for serialization.
    }

    public DeleteEntitiesAction getDeleteEntitiesAction() {
        return deleteEntitiesAction;
    }

    public DeleteEntitiesEvent(DeleteEntitiesAction deleteEntitiesAction) {
        this.deleteEntitiesAction = deleteEntitiesAction;
    }

    @Override
    protected void dispatch(DeleteEntitiesHandler handler) {
        handler.onDeleteEntities(this);
    }

    @Override
    public Type<DeleteEntitiesHandler> getAssociatedType() {
        return TYPE;
    }

    public static Type<DeleteEntitiesHandler> getType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, DeleteEntitiesAction deleteEntitiesAction) {
        DeleteEntitiesEvent eventInstance = new DeleteEntitiesEvent(deleteEntitiesAction);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, DeleteEntitiesEvent eventInstance) {
        source.fireEvent(eventInstance);
    }
}
