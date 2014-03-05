/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class EntitiesDeletedEvent extends GwtEvent<EntitiesDeletedEvent.EntitiesDeletedHandler> {
    public EntitiesDeletedEvent() {
    }

    public static void fire(HasHandlers source) {
        source.fireEvent(new EntitiesDeletedEvent());
    }

    public static void fire(HasHandlers source, EntitiesDeletedEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    public interface EntitiesDeletedHandler extends EventHandler {
        public void onEntitiesDeleted(EntitiesDeletedEvent event);
    }

    private static final Type<EntitiesDeletedHandler> TYPE = new Type<EntitiesDeletedHandler>();

    public static Type<EntitiesDeletedHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<EntitiesDeletedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EntitiesDeletedHandler handler) {
        handler.onEntitiesDeleted(this);
    }
}
