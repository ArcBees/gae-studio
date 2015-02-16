/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import java.util.List;

import com.arcbees.gaestudio.client.application.profiler.ui.TypeFilterCell;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.client.resources.RequestFilterCellListResources;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class TypeFilterView extends ViewWithUiHandlers<TypeFilterUiHandlers> implements TypeFilterPresenter.MyView {
    interface Binder extends UiBinder<Widget, TypeFilterView> {
    }

    @UiField(provided = true)
    CellList<FilterValue<OperationType>> requests;
    @UiField(provided = true)
    AppResources resources;

    private final SingleSelectionModel<FilterValue<OperationType>> selectionModel = new SingleSelectionModel<>();

    @Inject
    TypeFilterView(
            Binder uiBinder,
            AppResources resources,
            TypeFilterCell typeFilterCell,
            RequestFilterCellListResources cellListResources) {
        this.resources = resources;
        requests = new CellList<>(typeFilterCell, cellListResources);

        initWidget(uiBinder.createAndBindUi(this));

        initSelectionModel();
    }

    @Override
    public void display(List<FilterValue<OperationType>> filterValues) {
        requests.setRowData(filterValues);
    }

    private void initSelectionModel() {
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                FilterValue<OperationType> filterValue = selectionModel.getSelectedObject();
                getUiHandlers().onRequestClicked(filterValue);
            }
        });
        requests.setSelectionModel(selectionModel);
    }
}
