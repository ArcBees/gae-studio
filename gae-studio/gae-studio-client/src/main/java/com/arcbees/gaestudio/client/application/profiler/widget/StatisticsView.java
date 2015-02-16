/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.widget;

import com.arcbees.gaestudio.client.formatters.BytesFormatter;
import com.arcbees.gaestudio.client.util.TimeNumberFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class StatisticsView extends ViewImpl implements StatisticsPresenter.MyView {
    interface Binder extends UiBinder<Widget, StatisticsView> {
    }

    @UiField
    HTML requestCount;
    @UiField
    HTML statementCount;
    @UiField
    HTML totalExecutionTime;
    @UiField
    HTML totalObjectsRetrieved;
    @UiField
    HTML totalDataReceived;

    private final NumberFormat numberFormat = TimeNumberFormat.getFormat();
    private final BytesFormatter bytesFormatter;

    @Inject
    StatisticsView(
            Binder uiBinder,
            BytesFormatter bytesFormatter) {
        this.bytesFormatter = bytesFormatter;

        initWidget(uiBinder.createAndBindUi(this));

        updateRequestCount(0);
        updateStatementCount(0);
        updateTotalExecutionTimeMs(0);
        updateTotalObjectsRetrieved(0);
        updateTotalDataReceived(0);
    }

    @Override
    public void updateRequestCount(Integer requestCount) {
        this.requestCount.setHTML(requestCount.toString());
    }

    @Override
    public void updateStatementCount(Integer statementCount) {
        this.statementCount.setHTML(statementCount.toString());
    }

    @Override
    public void updateTotalExecutionTimeMs(Integer totalExecutionTimeMs) {
        this.totalExecutionTime.setHTML(numberFormat.format(totalExecutionTimeMs / 1000.0));
    }

    @Override
    public void updateTotalObjectsRetrieved(Integer totalObjectsRetrieved) {
        this.totalObjectsRetrieved.setHTML(totalObjectsRetrieved.toString());
    }

    @Override
    public void updateTotalDataReceived(Integer totalDataReceived) {
        this.totalDataReceived.setHTML(bytesFormatter.format(totalDataReceived));
    }
}
