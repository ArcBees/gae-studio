/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.ui;

import com.arcbees.gaestudio.client.formatters.RecordFormatter;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import javax.inject.Inject;

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
