/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.event;

import com.arcbees.gaestudio.client.dto.DbOperationRecordDto;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class StatementSelectedEvent extends GwtEvent<StatementSelectedEvent.StatementSelectedHandler> {
    private DbOperationRecordDto record;

    protected StatementSelectedEvent() {
        // Possibly for serialization.
    }

    public StatementSelectedEvent(DbOperationRecordDto record) {
        this.record = record;
    }

    public static void fire(HasHandlers source, DbOperationRecordDto record) {
        StatementSelectedEvent eventInstance = new StatementSelectedEvent(record);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, StatementSelectedEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    public interface HasStatementSelectedHandlers extends HasHandlers {
        HandlerRegistration addStatementSelectedHandler(StatementSelectedHandler handler);
    }

    public interface StatementSelectedHandler extends EventHandler {
        public void onStatementSelected(StatementSelectedEvent event);
    }

    private static final Type<StatementSelectedHandler> TYPE = new Type<StatementSelectedHandler>();

    public static Type<StatementSelectedHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<StatementSelectedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(StatementSelectedHandler handler) {
        handler.onStatementSelected(this);
    }

    public DbOperationRecordDto getRecord() {
        return record;
    }
}
