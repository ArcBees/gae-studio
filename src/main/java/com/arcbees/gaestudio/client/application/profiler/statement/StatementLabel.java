/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler.statement;

import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.ui.BaseLabel;
import com.arcbees.gaestudio.client.application.ui.LabelCallback;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDTO;
import com.arcbees.gaestudio.shared.formatters.RecordFormatter;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;

public class StatementLabel extends BaseLabel<Long> {

    private final RecordFormatter recordFormatter;

    @Inject
    public StatementLabel(final Resources resources, final RecordFormatter recordFormatter,
                          @Assisted final DbOperationRecordDTO record, @Assisted final LabelCallback<Long> callback) {
        super(resources, record.getStatementId(), callback);
        this.recordFormatter = recordFormatter;
        updateContent(record);
    }

    public void updateContent(DbOperationRecordDTO record) {
        setText(recordFormatter.formatRecord(record));
    }

}
