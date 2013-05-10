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
import com.arcbees.gaestudio.client.application.profiler.widget.filter.OperationType;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class TypeFilterCell extends AbstractCell<FilterValue<OperationType>> {
    private final AppConstants myConstants;

    @Inject
    public TypeFilterCell(AppConstants myConstants) {
        this.myConstants = myConstants;
    }

    @Override
    public void render(Context context, FilterValue<OperationType> filterValue, SafeHtmlBuilder safeHtmlBuilder) {
        safeHtmlBuilder.appendEscaped(getOperationTypeText(filterValue.getKey()));
        safeHtmlBuilder.appendEscaped(" [");
        safeHtmlBuilder.append(filterValue.getStatementCount());
        safeHtmlBuilder.appendEscaped("]");
    }

    private String getOperationTypeText(OperationType operationType) {
        switch (operationType) {
            case GET:
                return myConstants.getOperationType();
            case DELETE:
                return myConstants.deleteOperationType();
            case PUT:
                return myConstants.putOperationType();
            case QUERY:
                return myConstants.queryOperationType();
            default:
                return myConstants.unknownOperationType();
        }
    }
}
