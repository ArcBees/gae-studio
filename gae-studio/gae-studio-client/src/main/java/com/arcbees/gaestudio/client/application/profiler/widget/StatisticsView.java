/**
 * Copyright 2015 ArcBees Inc.
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
