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
    ColumnVisibilityWidget(
            ColumnVisibilityConfigHelper columnVisibilityConfigHelper,
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
