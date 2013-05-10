/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
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
import com.google.web.bindery.event.shared.HandlerRegistration;

public class RefreshEntitiesEvent extends GwtEvent<RefreshEntitiesEvent.RefreshEntitiesHandler> {
    public RefreshEntitiesEvent() {
        // Possibly for serialization.
    }

    public static void fire(HasHandlers source) {
        RefreshEntitiesEvent eventInstance = new RefreshEntitiesEvent();
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, RefreshEntitiesEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    public interface HasRefreshEntitiesHandlers extends HasHandlers {
        HandlerRegistration addRefreshEntitiesHandler(RefreshEntitiesHandler handler);
    }

    public interface RefreshEntitiesHandler extends EventHandler {
        public void onRefreshEntities(RefreshEntitiesEvent event);
    }

    private static final Type<RefreshEntitiesHandler> TYPE = new Type<RefreshEntitiesHandler>();

    public static Type<RefreshEntitiesHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<RefreshEntitiesHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(RefreshEntitiesHandler handler) {
        handler.onRefreshEntities(this);
    }
}
