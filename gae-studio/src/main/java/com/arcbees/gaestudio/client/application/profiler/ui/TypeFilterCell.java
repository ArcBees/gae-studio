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
import com.arcbees.gaestudio.client.application.profiler.widget.filter.OperationType;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import javax.inject.Inject;

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
