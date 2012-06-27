package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.profiler.ui.RequestFilterCell;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;

import java.util.List;

public class RequestFilterView extends ViewWithUiHandlers<RequestFilterUiHandlers> implements RequestFilterPresenter
        .MyView {

    public interface Binder extends UiBinder<Widget, RequestFilterView> {
    }

    @UiField(provided = true)
    CellList<FilterValue<Long>> requests;

    @UiField(provided = true)
    Resources resources;

    private final SingleSelectionModel<FilterValue<Long>> selectionModel = new
            SingleSelectionModel<FilterValue<Long>>();

    @Inject
    public RequestFilterView(final Binder uiBinder, final Resources resources,
                             final RequestFilterCell requestFilterCell,
                             final UiHandlersStrategy<RequestFilterUiHandlers> uiHandlersStrategy) {
        super(uiHandlersStrategy);

        this.resources = resources;
        requests = new CellList<FilterValue<Long>>(requestFilterCell);
        initWidget(uiBinder.createAndBindUi(this));
        initSelectionModel();
    }

    @Override
    public void display(List<FilterValue<Long>> filterValues) {
        requests.setRowData(filterValues);
    }

    private void initSelectionModel() {
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                FilterValue<Long> filterValue = selectionModel.getSelectedObject();
                getUiHandlers().onRequestClicked(filterValue);
            }
        });
        requests.setSelectionModel(selectionModel);
    }

}
