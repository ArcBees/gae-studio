package com.arcbees.gaestudio.client.application.profiler.ui;

import com.arcbees.gaestudio.client.MyMessages;
import com.arcbees.gaestudio.client.application.profiler.widget.filter.FilterValue;
import com.arcbees.gaestudio.client.util.TimeNumberFormat;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import javax.inject.Inject;

public class RequestFilterCell extends AbstractCell<FilterValue<Long>> {

    private static final NumberFormat numberFormat = TimeNumberFormat.getFormat();
    private final MyMessages myMessages;

    @Inject
    public RequestFilterCell(final MyMessages myMessages) {
        this.myMessages = myMessages;
    }

    @Override
    public void render(Context context, FilterValue<Long> filterValue, SafeHtmlBuilder safeHtmlBuilder) {
        safeHtmlBuilder.appendEscaped(myMessages.requestWithHisNumber(filterValue.getKey()));
        safeHtmlBuilder.appendEscaped(" - ");
        safeHtmlBuilder.appendEscaped(numberFormat.format(filterValue.getExecutionTimeMs() / 1000));
        safeHtmlBuilder.appendEscaped(" [");
        safeHtmlBuilder.append(filterValue.getStatementCount());
        safeHtmlBuilder.appendEscaped("]");
    }

}
