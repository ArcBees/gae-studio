package com.arcbees.gaestudio.client.application.profiler.ui;

import com.arcbees.gaestudio.client.application.profiler.widget.filter.FilterValue;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class TypeFilterCell extends AbstractCell<FilterValue<String>> {

    @Override
    public void render(Context context, FilterValue<String> filterValue, SafeHtmlBuilder safeHtmlBuilder) {
        safeHtmlBuilder.appendEscaped(filterValue.getKey());
        safeHtmlBuilder.appendEscaped(" [");
        safeHtmlBuilder.append(filterValue.getStatementCount());
        safeHtmlBuilder.appendEscaped("]");
    }

}
