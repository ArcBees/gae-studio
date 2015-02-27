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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class ColumnFilterPresenter extends PresenterWidget<ColumnFilterPresenter.MyView> implements
        TypeInfoLoadedEvent.TypeInfoLoadedHandler, ColumnFilterUiHandlers {
    interface MyView extends View, HasUiHandlers<ColumnFilterUiHandlers> {
        void clear();

        void display(IsWidget isWidget);
    }

    static final Object SLOT_COLUMNS = new Object();

    private final ColumnNamePresenterFactory columnNamePresenterFactory;

    private String appId;
    private String namespace;
    private String kind;
    private List<ColumnNamePresenter> columnNamePresenters;

    @Inject
    ColumnFilterPresenter(
            EventBus eventBus,
            MyView view,
            ColumnNamePresenterFactory columnNamePresenterFactory) {
        super(eventBus, view);

        this.columnNamePresenterFactory = columnNamePresenterFactory;
        this.columnNamePresenters = new ArrayList<>();

        getView().setUiHandlers(this);
    }

    @Override
    public void onTypeInfoLoaded(TypeInfoLoadedEvent event) {
        ParsedEntity entity = event.getPrototype();
        appId = entity.getKey().getAppIdNamespace().getAppId();
        namespace = entity.getKey().getAppIdNamespace().getNamespace();
        kind = entity.getKey().getKind();
        List<String> columnNames = event.getColumnNames();

        clearSlot(SLOT_COLUMNS);
        columnNamePresenters.clear();

        for (String columnName : columnNames) {
            ColumnNamePresenter columnNamePresenter =
                    columnNamePresenterFactory.create(appId, namespace, kind, columnName);
            columnNamePresenters.add(columnNamePresenter);
            addToSlot(SLOT_COLUMNS, columnNamePresenter);
        }
    }

    @Override
    public void showAll() {
        for (ColumnNamePresenter columnNamePresenter : columnNamePresenters) {
            columnNamePresenter.setChecked(true);
        }
    }

    @Override
    public void hideAll() {
        for (ColumnNamePresenter columnNamePresenter : columnNamePresenters) {
            columnNamePresenter.setChecked(false);
        }
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(TypeInfoLoadedEvent.getType(), this);
    }
}
