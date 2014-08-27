/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.columnfilter;

import java.util.List;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class ColumnFilterPresenter extends PresenterWidget<ColumnFilterPresenter.MyView> implements
        TypeInfoLoadedEvent.TypeInfoLoadedHandler {
    interface MyView extends View {
        void clear();

        void display(IsWidget isWidget);
    }

    private final ColumnVisibilityConfigHelper columnVisibilityConfigHelper;
    private final ColumnVisibilityWidgetFactory columnVisibilityWidgetFactory;
    static final String COLUMN_VISIBILITY_CONFIG = "column_visibility_config";

    @Inject
    ColumnFilterPresenter(EventBus eventBus,
                          MyView view,
                          ColumnVisibilityConfigHelper columnVisibilityConfigHelper,
                          ColumnVisibilityWidgetFactory columnVisibilityWidgetFactory) {
        super(eventBus, view);

        this.columnVisibilityConfigHelper = columnVisibilityConfigHelper;
        this.columnVisibilityWidgetFactory = columnVisibilityWidgetFactory;
    }

    @Override
    public void onTypeInfoLoaded(TypeInfoLoadedEvent event) {
        ParsedEntity entity = event.getPrototype();
        String namespace = entity.getKey().getAppIdNamespace().getNamespace();
        String appId = entity.getKey().getAppIdNamespace().getAppId();
        String kind = entity.getKey().getKind();
        List<String> columnNames = event.getColumnNames();

        GQuery.console.info("here");

        columnVisibilityConfigHelper.addNewColumnsIfNeeded(appId, namespace, kind, columnNames);

        getView().clear();

        for (String columnName : columnNames) {
            getView().display(columnVisibilityWidgetFactory.create(appId, namespace, kind, columnName));
        }
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(TypeInfoLoadedEvent.getType(), this);
    }
}
