package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import java.util.List;

import com.arcbees.gaestudio.client.application.profiler.ui.RequestFilterCell;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class RequestFilterView extends ViewWithUiHandlers<RequestFilterUiHandlers> implements
        RequestFilterPresenter.MyView {

    public interface Binder extends UiBinder<Widget, RequestFilterView> {
    }

    @UiField(provided = true)
    CellList<FilterValue<Long>> requests;

    @UiField(provided = true)
    AppResources resources;

    private final SingleSelectionModel<FilterValue<Long>> selectionModel = new SingleSelectionModel<FilterValue<Long>>();

    @Inject
    public RequestFilterView(final Binder uiBinder, final AppResources resources, final RequestFilterCell requestFilterCell) {
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
