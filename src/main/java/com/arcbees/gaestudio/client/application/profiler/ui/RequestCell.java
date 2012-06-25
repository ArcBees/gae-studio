package com.arcbees.gaestudio.client.application.profiler.ui;

import com.arcbees.gaestudio.client.application.profiler.widget.filter.RequestStatistics;
import com.arcbees.gaestudio.client.util.TimeNumberFormat;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class RequestCell extends AbstractCell<RequestStatistics> {

    private static final NumberFormat numberFormat = TimeNumberFormat.getFormat();

    @Override
    public void render(Context context, RequestStatistics requestStatistics, SafeHtmlBuilder safeHtmlBuilder) {
        safeHtmlBuilder.appendEscaped("Request #");
        safeHtmlBuilder.append(requestStatistics.getRequestId());
        safeHtmlBuilder.appendEscaped(" - ");
        safeHtmlBuilder.appendEscaped(numberFormat.format(requestStatistics.getExecutionTimeMs() / 1000.0));
        safeHtmlBuilder.appendEscaped(" [");
        safeHtmlBuilder.append(requestStatistics.getStatementCount());
        safeHtmlBuilder.appendEscaped("]");
    }

}
