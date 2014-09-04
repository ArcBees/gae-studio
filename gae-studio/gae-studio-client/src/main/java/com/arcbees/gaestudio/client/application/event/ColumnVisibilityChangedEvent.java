/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class ColumnVisibilityChangedEvent
        extends GwtEvent<ColumnVisibilityChangedEvent.ColumnVisibilityChangedHandler> {
    public ColumnVisibilityChangedEvent() {
    }

    public static void fire(HasHandlers source) {
        ColumnVisibilityChangedEvent eventInstance = new ColumnVisibilityChangedEvent();
        source.fireEvent(eventInstance);
    }

    public interface ColumnVisibilityChangedHandler extends EventHandler {
        public void onColumnVisibilityChanged(ColumnVisibilityChangedEvent event);
    }

    private static final Type<ColumnVisibilityChangedHandler> TYPE = new Type<>();

    public static Type<ColumnVisibilityChangedHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<ColumnVisibilityChangedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ColumnVisibilityChangedHandler handler) {
        handler.onColumnVisibilityChanged(this);
    }
}
