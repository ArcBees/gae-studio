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

package com.arcbees.gaestudio.client.application.visualizer.widget;

import java.util.List;

import com.arcbees.gaestudio.client.application.visualizer.columnfilter.ColumnVisibilityConfigHelper;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;

public class EntityCellTable<T> extends CellTable<T> {
    private final ColumnVisibilityConfigHelper columnVisibilityConfigHelper;
    private final AppResources appResources;

    private String appId;
    private String namespace;
    private String kind;

    public EntityCellTable(
            int pageSize,
            Resources resources,
            AppResources appResources,
            ColumnVisibilityConfigHelper columnVisibilityConfigHelper) {
        super(pageSize, resources);

        this.columnVisibilityConfigHelper = columnVisibilityConfigHelper;
        this.appResources = appResources;
    }

    public void setKind(String appId, String namespace, String kind) {
        this.appId = appId;
        this.namespace = namespace;
        this.kind = kind;
    }

    @Override
    protected void replaceAllChildren(List<T> values, SafeHtml html) {
        refreshCellVisibility();

        super.replaceAllChildren(values, html);
    }

    public void updateCellVisibility() {
        refreshCellVisibility();

        redraw();
    }

    private void refreshCellVisibility() {
        if (appId == null || namespace == null || kind == null) {
            return;
        }

        for (int i = 0; i < getColumnCount(); i++) {
            Header<?> header = getHeader(i);
            Column<T, ?> column = getColumn(i);

            String headerName = (String) header.getValue();
            Boolean columnShouldBeVisible = columnVisibilityConfigHelper
                    .getColumnVisibility(appId, namespace, kind, headerName);

            if (columnShouldBeVisible) {
                column.setCellStyleNames(null);
                header.setHeaderStyleNames(null);
            } else {
                column.setCellStyleNames(appResources.styles().hiddenCol());
                header.setHeaderStyleNames(appResources.styles().hiddenCol());
            }
        }
    }
}
