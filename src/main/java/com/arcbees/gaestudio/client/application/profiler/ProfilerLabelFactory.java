/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.gaestudio.client.application.profiler.request.RequestLabel;
import com.arcbees.gaestudio.client.application.profiler.request.RequestStatistics;
import com.arcbees.gaestudio.client.application.profiler.statement.StatementLabel;
import com.arcbees.gaestudio.client.application.ui.LabelCallback;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDTO;

public interface ProfilerLabelFactory {

    RequestLabel createRequest(final RequestStatistics requestStatistics, final LabelCallback callback);

    StatementLabel createStatement(final DbOperationRecordDTO record, final LabelCallback callback);

}
