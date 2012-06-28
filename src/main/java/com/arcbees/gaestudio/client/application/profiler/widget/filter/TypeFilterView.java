package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.profiler.ui.TypeFilterCell;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;

import java.util.List;

public class TypeFilterView extends ViewWithUiHandlers<TypeFilterUiHandlers> implements TypeFilterPresenter
        .MyView {

    public interface Binder extends UiBinder<Widget, TypeFilterView> {
    }

    @UiField(provided = true)
    CellList<FilterValue<OperationType>> requests;

    @UiField(provided = true)
    Resources resources;

    private final SingleSelectionModel<FilterValue<OperationType>> selectionModel =
            new SingleSelectionModel<FilterValue<OperationType>>();

    @Inject
    public TypeFilterView(final Binder uiBinder, final Resources resources, final TypeFilterCell typeFilterCell,
                          final UiHandlersStrategy<TypeFilterUiHandlers> uiHandlersStrategy) {
        super(uiHandlersStrategy);

        this.resources = resources;
        requests = new CellList<FilterValue<OperationType>>(typeFilterCell);
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
