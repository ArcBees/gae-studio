package com.arcbees.gaestudio.client.application.profiler.ui;

import com.arcbees.gaestudio.client.application.profiler.widget.filter.FilterValue;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class FilterCell<T> extends AbstractCell<FilterValue<T>> {

    @Override
    public void render(Context context, FilterValue<T> filterValue, SafeHtmlBuilder safeHtmlBuilder) {
        safeHtmlBuilder.appendEscaped(filterValue.toString());
    }

}
