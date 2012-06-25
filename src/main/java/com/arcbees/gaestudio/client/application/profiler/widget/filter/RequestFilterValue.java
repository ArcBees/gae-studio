package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import com.arcbees.gaestudio.client.util.TimeNumberFormat;
import com.google.gwt.i18n.client.NumberFormat;

public class RequestFilterValue extends FilterValue<Long> {

    private static final NumberFormat numberFormat = TimeNumberFormat.getFormat();

    public RequestFilterValue(Long key) {
        super(key);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Request #");
        stringBuilder.append(getKey());
        stringBuilder.append(" - ");
        stringBuilder.append(numberFormat.format(getExecutionTimeMs() / 1000));
        stringBuilder.append(" [");
        stringBuilder.append(getStatementCount());
        stringBuilder.append("]");

        return stringBuilder.toString();
    }
}
