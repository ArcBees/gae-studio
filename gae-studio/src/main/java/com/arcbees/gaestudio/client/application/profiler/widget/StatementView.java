/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.widget;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StatementView extends ViewWithUiHandlers<StatementUiHandlers> implements StatementPresenter.MyView {
    interface Binder extends UiBinder<Widget, StatementView> {
    }

    @UiField(provided = true)
    AppResources resources;

    @UiField(provided = true)
    CellList<DbOperationRecordDto> statements;

    private final SingleSelectionModel<DbOperationRecordDto> selectionModel =
            new SingleSelectionModel<DbOperationRecordDto>();

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
