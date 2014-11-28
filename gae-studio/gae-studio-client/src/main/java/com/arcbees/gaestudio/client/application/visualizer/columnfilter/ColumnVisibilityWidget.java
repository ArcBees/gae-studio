/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.columnfilter;

import javax.inject.Inject;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.client.application.visualizer.columnfilter.ColumnVisibilityWidgetFactory.APP_ID;
import static com.arcbees.gaestudio.client.application.visualizer.columnfilter.ColumnVisibilityWidgetFactory
        .COLUMN_NAME;
import static com.arcbees.gaestudio.client.application.visualizer.columnfilter.ColumnVisibilityWidgetFactory.KIND;
import static com.arcbees.gaestudio.client.application.visualizer.columnfilter.ColumnVisibilityWidgetFactory.NAMESPACE;

public class ColumnVisibilityWidget implements IsWidget, ValueChangeHandler<Boolean> {
    private final String appId;
    private final String namespace;
    private final String kind;
    private final String columnName;
    private final CheckBox checkBox;
    private final ColumnVisibilityConfigHelper columnVisibilityConfigHelper;

    @Inject
    ColumnVisibilityWidget(ColumnVisibilityConfigHelper columnVisibilityConfigHelper,
                           @Assisted(APP_ID) String appId,
                           @Assisted(NAMESPACE) String namespace,
                           @Assisted(KIND) String kind,
                           @Assisted(COLUMN_NAME) String columnName) {
        this.appId = appId;
        this.namespace = namespace;
        this.kind = kind;
        this.columnName = columnName;
        this.columnVisibilityConfigHelper = columnVisibilityConfigHelper;

        checkBox = new CheckBox(columnName);

        checkBox.setValue(columnVisibilityConfigHelper.getColumnVisibility(appId, namespace, kind, columnName));

        checkBox.addValueChangeHandler(this);
    }

    @Override
    public Widget asWidget() {
        return checkBox;
    }

    @Override
    public void onValueChange(ValueChangeEvent<Boolean> event) {
        columnVisibilityConfigHelper.setColumnVisible(appId, namespace, kind, columnName, event.getValue());
    }
}
