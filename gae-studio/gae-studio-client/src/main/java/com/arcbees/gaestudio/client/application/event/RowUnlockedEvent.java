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

public class RowUnlockedEvent extends GwtEvent<RowUnlockedEvent.RowUnlockedHandler> {
    public interface RowUnlockedHandler extends EventHandler {
        void onRowUnlocked(RowUnlockedEvent rowLockedEvent);
    }

    public static void fire(HasHandlers source) {
        source.fireEvent(new RowUnlockedEvent());
    }

    public static Type<RowUnlockedHandler> getType() {
        return TYPE;
    }

    private static final Type<RowUnlockedHandler> TYPE = new Type<RowUnlockedHandler>();

    private RowUnlockedEvent() {
    }

    @Override
    public Type<RowUnlockedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(RowUnlockedHandler handler) {
        handler.onRowUnlocked(this);
    }
}
