/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.widget;

import com.arcbees.gaestudio.client.formatters.RecordFormatter;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.arcbees.gaestudio.shared.dto.stacktrace.StackTraceElementDto;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class DetailsView extends ViewImpl implements DetailsPresenter.MyView {
    interface Binder extends UiBinder<Widget, DetailsView> {
    }

    @UiField
    HTML statement;

    @UiField
    HTML callLocation;

    @UiField(provided = true)
    AppResources resources;

    private final RecordFormatter recordFormatter;

    @Inject
    DetailsView(Binder uiBinder,
                AppResources resources,
                RecordFormatter recordFormatter) {
        this.resources = resources;

        initWidget(uiBinder.createAndBindUi(this));

        this.recordFormatter = recordFormatter;
    }

    @Override
    public void displayStatementDetails(DbOperationRecordDto record) {
        statement.setHTML(recordFormatter.formatRecord(record));
        callLocation.setHTML(tempFormatCaller(record.getCallerStackTraceElement()));
    }

    @Override
    public void clear() {
        statement.setHTML("");
        callLocation.setHTML("");
    }

    private String tempFormatCaller(StackTraceElementDto caller) {
        StringBuilder builder = new StringBuilder();

        builder.append("Class:");
        builder.append(caller.getClassName());
        builder.append(" Method:");
        builder.append(caller.getMethodName());
        builder.append(" Filename:");
        builder.append(caller.getFileName());
        builder.append(" Line#:");
        builder.append(caller.getLineNumber());

        return builder.toString();
    }
}
