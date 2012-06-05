package com.arcbees.gaestudio.client.application.profiler.statement;

import com.arcbees.gaestudio.shared.dto.DbOperationRecordDTO;
import com.arcbees.gaestudio.shared.formatters.RecordFormatter;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import javax.inject.Inject;

public class StatementCell extends AbstractCell<DbOperationRecordDTO> {

    private RecordFormatter recordFormatter;

    @Inject
    public StatementCell(final RecordFormatter recordFormatter) {
        this.recordFormatter = recordFormatter;
    }

    @Override
    public void render(Context context, DbOperationRecordDTO record, SafeHtmlBuilder safeHtmlBuilder) {
        safeHtmlBuilder.appendEscaped(recordFormatter.formatRecord(record));
    }

}
