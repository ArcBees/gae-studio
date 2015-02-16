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
