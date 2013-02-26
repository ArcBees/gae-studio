/*
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.gaestudio.client.application.profiler.ui;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.profiler.widget.filter.FilterValue;
import com.arcbees.gaestudio.client.resources.AppMessages;
import com.arcbees.gaestudio.client.util.TimeNumberFormat;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class RequestFilterCell extends AbstractCell<FilterValue<Long>> {
    private static final NumberFormat numberFormat = TimeNumberFormat.getFormat();
    private final AppMessages myMessages;

    @Inject
    public RequestFilterCell(final AppMessages myMessages) {
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
