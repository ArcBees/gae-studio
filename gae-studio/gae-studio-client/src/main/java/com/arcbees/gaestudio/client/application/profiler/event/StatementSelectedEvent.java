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

package com.arcbees.gaestudio.client.application.profiler.event;

import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class StatementSelectedEvent extends GwtEvent<StatementSelectedEvent.StatementSelectedHandler> {
    private static final Type<StatementSelectedHandler> TYPE = new Type<StatementSelectedHandler>();

    private DbOperationRecordDto record;

    public StatementSelectedEvent(DbOperationRecordDto record) {
        this.record = record;
    }

    protected StatementSelectedEvent() {
        // Possibly for serialization.
    }

    public static void fire(HasHandlers source, DbOperationRecordDto record) {
        StatementSelectedEvent eventInstance = new StatementSelectedEvent(record);
        source.fireEvent(eventInstance);
    }

    public interface StatementSelectedHandler extends EventHandler {
        void onStatementSelected(StatementSelectedEvent event);
    }

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
