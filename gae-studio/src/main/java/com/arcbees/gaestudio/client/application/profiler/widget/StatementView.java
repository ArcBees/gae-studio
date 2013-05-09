/*
 * Copyright 2013 ArcBees Inc.
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.arcbees.gaestudio.client.application.profiler.ui.StatementCell;
import com.arcbees.gaestudio.client.application.profiler.widget.filter.FilterValue;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class StatementView extends ViewWithUiHandlers<StatementUiHandlers> implements StatementPresenter.MyView {
    interface Binder extends UiBinder<Widget, StatementView> {
    }

    @UiField(provided = true)
    AppResources resources;

    @UiField(provided = true)
    CellList<DbOperationRecordDto> statements;

    private final SingleSelectionModel<DbOperationRecordDto> selectionModel = new
            SingleSelectionModel<DbOperationRecordDto>();

    @Inject
    StatementView(Binder uiBinder,
                  AppResources resources,
                  StatementCell statementCell) {
        this.resources = resources;
        statements = new CellList<DbOperationRecordDto>(statementCell);

        initWidget(uiBinder.createAndBindUi(this));

        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                DbOperationRecordDto dbOperationRecordDTO = selectionModel.getSelectedObject();
                getUiHandlers().onStatementClicked(dbOperationRecordDTO);
            }
        });
        statements.setSelectionModel(selectionModel);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void displayStatements(FilterValue<?> filterValue) {
        List statements = filterValue.getStatements();
        Collections.sort(statements, new StatementIdComparator());

        this.statements.setRowData(statements);
    }

    @Override
    public void clear() {
        statements.setRowData(new ArrayList<DbOperationRecordDto>());
    }

    private class StatementIdComparator implements Comparator<DbOperationRecordDto> {
        @Override
        public int compare(DbOperationRecordDto o1, DbOperationRecordDto o2) {
            return o1.getStatementId().compareTo(o2.getStatementId());
        }
    }
}
