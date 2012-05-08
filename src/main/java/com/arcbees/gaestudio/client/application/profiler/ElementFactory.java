/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.gaestudio.client.application.profiler.request.RequestElement;
import com.arcbees.gaestudio.client.application.profiler.request.RequestStatistics;
import com.arcbees.gaestudio.client.application.profiler.statement.StatementElement;
import com.arcbees.gaestudio.shared.dto.DbOperationRecord;

public interface ElementFactory {
    RequestElement createRequest(final RequestStatistics requestStatistics,
                                 final ElementCallback callback);

    StatementElement createStatement(final DbOperationRecord record,
                                     final ElementCallback callback);
}
