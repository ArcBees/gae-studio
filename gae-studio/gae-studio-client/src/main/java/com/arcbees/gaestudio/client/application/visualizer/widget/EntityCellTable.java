/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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
