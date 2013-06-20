/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class ClearOperationRecordsEvent extends GwtEvent<ClearOperationRecordsEvent.ClearOperationRecordsHandler> {
    public ClearOperationRecordsEvent() {
        // Possibly for serialization.
    }

    public static void fire(HasHandlers source) {
        ClearOperationRecordsEvent eventInstance = new ClearOperationRecordsEvent();
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, ClearOperationRecordsEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    public interface HasClearOperationRecordsHandlers extends HasHandlers {
        HandlerRegistration addClearOperationRecordsHandler(ClearOperationRecordsHandler handler);
    }

    public interface ClearOperationRecordsHandler extends EventHandler {
        public void onClearOperationRecords(ClearOperationRecordsEvent event);
    }

    private static final Type<ClearOperationRecordsHandler> TYPE = new Type<ClearOperationRecordsHandler>();

    public static Type<ClearOperationRecordsHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<ClearOperationRecordsHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ClearOperationRecordsHandler handler) {
        handler.onClearOperationRecords(this);
    }
}
