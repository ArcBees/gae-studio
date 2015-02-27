/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.gaestudio.client.application.event;

import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class DbOperationEvent extends GwtEvent<DbOperationEvent.DbOperationHandler> {
    public interface DbOperationHandler extends EventHandler {
        void onDbOperation(DbOperationEvent event);
    }

    private static final Type<DbOperationHandler> TYPE = new Type<>();

    private final DbOperationRecordDto dbOperationRecordDto;

    private DbOperationEvent(DbOperationRecordDto dbOperationRecordDto) {
        this.dbOperationRecordDto = dbOperationRecordDto;
    }

    public static void fire(HasHandlers hasHandlers, DbOperationRecordDto dbOperationRecordDto) {
        hasHandlers.fireEvent(new DbOperationEvent(dbOperationRecordDto));
    }

    public static Type<DbOperationHandler> getType() {
        return TYPE;
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
