package com.arcbees.gaestudio.client.application.profiler.ui;

import com.arcbees.gaestudio.client.formatters.RecordFormatter;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import javax.inject.Inject;

public class StatementCell extends AbstractCell<DbOperationRecordDto> {

    private RecordFormatter recordFormatter;

    @Inject
    public StatementCell(final RecordFormatter recordFormatter) {
        this.recordFormatter = recordFormatter;
    }

    @Override
    public void render(Context context, DbOperationRecordDto record, SafeHtmlBuilder safeHtmlBuilder) {
        safeHtmlBuilder.appendEscaped(recordFormatter.formatRecord(record));
    }

}
