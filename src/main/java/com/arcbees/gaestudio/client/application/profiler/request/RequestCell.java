package com.arcbees.gaestudio.client.application.profiler.request;

import com.arcbees.gaestudio.client.application.profiler.TimeNumberFormat;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class RequestCell extends AbstractCell<RequestStatistics> {

    private static final NumberFormat numberFormat = TimeNumberFormat.getFormat();

    @Override
    public void render(Context context, RequestStatistics requestStatistics, SafeHtmlBuilder safeHtmlBuilder) {
        String content = "Request #" + requestStatistics.getRequestId() + " - " + numberFormat.format
                (requestStatistics.getExecutionTimeMs() / 1000.0) + " [" + requestStatistics.getStatementCount() + "]";

        safeHtmlBuilder.appendEscaped(content);
    }
}
