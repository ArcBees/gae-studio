/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.event;

import com.arcbees.gaestudio.shared.dispatch.DeleteEntitiesType;
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

    private DeleteEntitiesType deleteType;
    private String value;

    protected DeleteEntitiesEvent() {
        // Possibly for serialization.
    }

    public DeleteEntitiesType getDeleteType() {
        return deleteType;
    }

    public String getValue() {
        return value;
    }

    public DeleteEntitiesEvent(DeleteEntitiesType deleteType, String value) {
        this.deleteType = deleteType;
        this.value = value;
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

    public static void fire(HasHandlers source, DeleteEntitiesType deleteType) {
        fire(source, deleteType, "");
    }

    public static void fire(HasHandlers source, DeleteEntitiesType deleteType, String value) {
        DeleteEntitiesEvent eventInstance = new DeleteEntitiesEvent(deleteType, value);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, DeleteEntitiesEvent eventInstance) {
        source.fireEvent(eventInstance);
    }
}
