/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.ui;

import com.arcbees.gaestudio.client.application.profiler.widget.filter.FilterValue;
import com.arcbees.gaestudio.client.resources.AppMessages;
import com.arcbees.gaestudio.client.util.TimeNumberFormat;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import javax.inject.Inject;

public class RequestFilterCell extends AbstractCell<FilterValue<Long>> {
    private static final NumberFormat numberFormat = TimeNumberFormat.getFormat();
    private final AppMessages myMessages;

    @Inject
    public RequestFilterCell(AppMessages myMessages) {
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
