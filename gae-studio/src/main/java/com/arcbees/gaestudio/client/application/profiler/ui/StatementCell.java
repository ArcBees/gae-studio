/*
 * Copyright 2013 ArcBees Inc.
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

package com.arcbees.gaestudio.client.application.profiler.ui;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.formatters.RecordFormatter;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class StatementCell extends AbstractCell<DbOperationRecordDto> {
    private RecordFormatter recordFormatter;

    @Inject
    public StatementCell(RecordFormatter recordFormatter) {
        this.recordFormatter = recordFormatter;
    }

    @Override
    public void render(Context context, DbOperationRecordDto record, SafeHtmlBuilder safeHtmlBuilder) {
        safeHtmlBuilder.appendEscaped(recordFormatter.formatRecord(record));
    }
}
