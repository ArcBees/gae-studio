/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.client.application.visualizer.kind;

import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.BaseLabel;
import com.arcbees.gaestudio.client.application.LabelCallback;
import com.arcbees.gaestudio.client.application.profiler.TimeNumberFormat;
import com.arcbees.gaestudio.client.application.profiler.request.RequestStatistics;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;

public class KindLabel extends BaseLabel {

    private static final NumberFormat numberFormat = TimeNumberFormat.getFormat();

    @Inject
    public KindLabel(final Resources resources, @Assisted final RequestStatistics requestStatistics,
                     @Assisted final LabelCallback callback) {
        super(resources, requestStatistics.getRequestId(), callback);
        updateContent(requestStatistics);
    }

    public void updateContent(RequestStatistics requestStatistics) {
        String content = "Request #" + requestStatistics.getRequestId() + " - " + numberFormat.format
                (requestStatistics.getExecutionTimeMs() / 1000.0) + " [" + requestStatistics.getStatementCount() + "]";

        setText(content);
    }

}
