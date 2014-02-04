/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
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
import com.google.web.bindery.event.shared.HandlerRegistration;

public class EditEntityEvent extends GwtEvent<EditEntityEvent.EditEntityHandler> {
    private ParsedEntity parsedEntity;

    protected EditEntityEvent() {
        // Possibly for serialization.
    }

    public EditEntityEvent(ParsedEntity parsedEntity) {
        this.parsedEntity = parsedEntity;
    }

    public static void fire(HasHandlers source, ParsedEntity parsedEntity) {
        EditEntityEvent eventInstance = new EditEntityEvent(parsedEntity);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, EditEntityEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    public interface HasEditEntityHandlers extends HasHandlers {
        HandlerRegistration addEditEntityHandler(EditEntityHandler handler);
    }

    public interface EditEntityHandler extends EventHandler {
        public void onEditEntity(EditEntityEvent event);
    }

    private static final Type<EditEntityHandler> TYPE = new Type<EditEntityHandler>();

    public static Type<EditEntityHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<EditEntityHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EditEntityHandler handler) {
        handler.onEditEntity(this);
    }

    public ParsedEntity getParsedEntity() {
        return parsedEntity;
    }
}
