/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.event;

import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class DbOperationEvent extends GwtEvent<DbOperationEvent.DbOperationHandler> {
    private final DbOperationRecordDto dbOperationRecordDto;

    public interface DbOperationHandler extends EventHandler {
        void onDbOperation(DbOperationEvent event);
    }

    public static void fire(HasHandlers hasHandlers, DbOperationRecordDto dbOperationRecordDto) {
        hasHandlers.fireEvent(new DbOperationEvent(dbOperationRecordDto));
    }

    public static Type<DbOperationHandler> getType() {
        return TYPE;
    }

    private static final Type<DbOperationHandler> TYPE = new Type<>();

    private DbOperationEvent(DbOperationRecordDto dbOperationRecordDto) {
        this.dbOperationRecordDto = dbOperationRecordDto;
    }

    public DbOperationRecordDto getDbOperationRecordDto() {
        return dbOperationRecordDto;
    }

    @Override
    public Type<DbOperationHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DbOperationHandler handler) {
        handler.onDbOperation(this);
    }
}
