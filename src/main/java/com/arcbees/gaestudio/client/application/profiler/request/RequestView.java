package com.arcbees.gaestudio.client.application.profiler.request;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.Resources;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;

import java.util.List;

public class RequestView extends ViewWithUiHandlers<RequestUiHandlers> implements RequestPresenter.MyView {

    public interface Binder extends UiBinder<Widget, RequestView> {
    }

    @UiField(provided = true)
    CellList<RequestStatistics> requests;

    @UiField(provided = true)
    Resources resources;

    private final SingleSelectionModel<RequestStatistics> selectionModel = new
            SingleSelectionModel<RequestStatistics>();

    @Inject
    public RequestView(final Binder uiBinder, final UiHandlersStrategy<RequestUiHandlers> uiHandlersStrategy,
                       final Resources resources, final RequestCell requestCell) {
        super(uiHandlersStrategy);

        this.resources = resources;
        requests = new CellList<RequestStatistics>(requestCell);
        initWidget(uiBinder.createAndBindUi(this));

        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                RequestStatistics requestStatistics = selectionModel.getSelectedObject();
                getUiHandlers().onRequestClicked(requestStatistics.getRequestId());
            }
        });
        requests.setSelectionModel(selectionModel);
    }

    @Override
    public void displayRequests(List<RequestStatistics> requestStatistics) {
         requests.setRowData(requestStatistics);
    }

}
