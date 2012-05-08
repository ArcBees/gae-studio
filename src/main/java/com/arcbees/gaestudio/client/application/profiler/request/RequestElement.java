/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler.request;

import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.profiler.Element;
import com.arcbees.gaestudio.client.application.profiler.ElementCallback;
import com.arcbees.gaestudio.client.application.profiler.TimeNumberFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;

public class RequestElement extends Element {

    private static final NumberFormat numberFormat = TimeNumberFormat.getFormat();

    @Inject
    public RequestElement(final Resources resources, @Assisted final RequestStatistics requestStatistics,
                          @Assisted final ElementCallback callback) {
        super(resources, requestStatistics.getRequestId(), callback);
        updateContent(requestStatistics);
    }

    public void updateContent(RequestStatistics requestStatistics) {
        StringBuilder builder = new StringBuilder();

        builder.append("Request #");
        builder.append(requestStatistics.getRequestId());
        builder.append(" - ");
        builder.append(numberFormat.format(requestStatistics.getExecutionTimeMs() / 1000.0));
        builder.append(" [");
        builder.append(requestStatistics.getStatementCount());
        builder.append("]");

        setText(builder.toString());
    }
}
