/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler.statement;

import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.profiler.Element;
import com.arcbees.gaestudio.client.application.profiler.ElementCallback;
import com.arcbees.gaestudio.shared.dto.DbOperationRecord;
import com.arcbees.gaestudio.shared.formatters.RecordFormatter;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;

public class StatementElement extends Element {

    private final RecordFormatter recordFormatter;

    @Inject
    public StatementElement(final Resources resources, final RecordFormatter recordFormatter,
                            @Assisted final DbOperationRecord record, @Assisted final ElementCallback callback) {
        super(resources, record.getStatementId(), callback);
        this.recordFormatter = recordFormatter;
        updateContent(record);
    }

    public void updateContent(DbOperationRecord record) {
        setText(recordFormatter.formatRecord(record));
    }

}
