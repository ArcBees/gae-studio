/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.gaestudio.client.application.profiler.request.RequestLabel;
import com.arcbees.gaestudio.client.application.profiler.request.RequestStatistics;
import com.arcbees.gaestudio.client.application.profiler.statement.StatementLabel;
import com.arcbees.gaestudio.client.application.ui.LabelCallback;
import com.arcbees.gaestudio.shared.dto.DbOperationRecord;

public interface ProfilerLabelFactory {

    RequestLabel createRequest(final RequestStatistics requestStatistics, final LabelCallback<Long> callback);

    StatementLabel createStatement(final DbOperationRecord record, final LabelCallback<Long> callback);

}
