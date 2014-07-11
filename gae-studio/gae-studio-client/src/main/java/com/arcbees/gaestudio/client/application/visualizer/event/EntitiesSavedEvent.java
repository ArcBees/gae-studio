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

public class EntitiesSavedEvent extends GwtEvent<EntitiesSavedEvent.EntitiesSavedHandler> {
    public interface EntitiesSavedHandler extends EventHandler {
        public void onEntitiesSaved(EntitiesSavedEvent event);
    }

    public static void fire(HasHandlers source) {
        source.fireEvent(new EntitiesSavedEvent());
    }

    public static Type<EntitiesSavedHandler> getType() {
        return TYPE;
    }

    private static final Type<EntitiesSavedHandler> TYPE = new Type<>();

    private EntitiesSavedEvent() {
    }

    @Override
    public Type<EntitiesSavedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EntitiesSavedHandler handler) {
        handler.onEntitiesSaved(this);
    }
}
