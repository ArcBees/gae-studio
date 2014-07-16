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

public class ImportCompletedEvent extends GwtEvent<ImportCompletedEvent.ImportCompletedHandler> {
    public interface ImportCompletedHandler extends EventHandler {
        void onImportComplete(ImportCompletedEvent event);
    }

    public static void fire(HasHandlers hasHandlers) {
        hasHandlers.fireEvent(new ImportCompletedEvent());
    }

    public static Type<ImportCompletedHandler> getType() {
        return TYPE;
    }

    private static final Type<ImportCompletedHandler> TYPE = new Type<>();

    private ImportCompletedEvent() {
    }

    @Override
    public Type<ImportCompletedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ImportCompletedHandler handler) {
        handler.onImportComplete(this);
    }
}
