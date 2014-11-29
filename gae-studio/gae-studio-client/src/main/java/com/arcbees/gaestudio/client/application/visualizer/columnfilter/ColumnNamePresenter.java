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

import com.arcbees.gaestudio.client.application.event.ColumnVisibilityChangedEvent;
import com.google.inject.assistedinject.Assisted;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import static com.arcbees.gaestudio.client.application.visualizer.columnfilter.ColumnNamePresenterFactory.APP_ID;
import static com.arcbees.gaestudio.client.application.visualizer.columnfilter.ColumnNamePresenterFactory.COLUMN_NAME;
import static com.arcbees.gaestudio.client.application.visualizer.columnfilter.ColumnNamePresenterFactory.KIND;
import static com.arcbees.gaestudio.client.application.visualizer.columnfilter.ColumnNamePresenterFactory.NAMESPACE;

public class ColumnNamePresenter extends PresenterWidget<ColumnNamePresenter.MyView> implements ColumnNameUiHandlers {
    interface MyView extends View, HasUiHandlers<ColumnNameUiHandlers> {
        void setColumnName(String columnName);

        void setChecked(boolean checked);
    }

    private final String appId;
    private final String namespace;
    private final String kind;
    private final String columnName;
    private final ColumnVisibilityConfigHelper columnVisibilityConfigHelper;

    @Inject
    ColumnNamePresenter(EventBus eventBus,
                        MyView myView,
                        ColumnVisibilityConfigHelper columnVisibilityConfigHelper,
                        @Assisted(APP_ID) String appId,
                        @Assisted(NAMESPACE) String namespace,
                        @Assisted(KIND) String kind,
                        @Assisted(COLUMN_NAME) String columnName) {
        super(eventBus, myView);

        this.appId = appId;
        this.namespace = namespace;
        this.kind = kind;
        this.columnName = columnName;
        this.columnVisibilityConfigHelper = columnVisibilityConfigHelper;

        getView().setUiHandlers(this);
    }

    @Override
    public void onValueChange(Boolean value) {
        columnVisibilityConfigHelper.setColumnVisible(appId, namespace, kind, columnName, value);

        ColumnVisibilityChangedEvent.fire(this);
    }

    public void setChecked(boolean checked) {
        getView().setChecked(checked);

        onValueChange(checked);
    }

    @Override
    protected void onBind() {
        super.onBind();

        getView().setColumnName(columnName);
        getView().setChecked(columnVisibilityConfigHelper.getColumnVisibility(appId, namespace, kind, columnName));
    }
}
