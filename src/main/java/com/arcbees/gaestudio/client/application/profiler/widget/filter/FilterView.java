package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.profiler.ui.RequestCell;
import com.arcbees.gaestudio.client.application.profiler.widget.RequestStatistics;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;

import java.util.List;

public class FilterView extends ViewWithUiHandlers<FilterUiHandlers> implements FilterPresenter.MyView {

    public interface Binder extends UiBinder<Widget, FilterView> {
    }

    @UiField(provided = true)
    CellList<RequestStatistics> requests;

    @UiField(provided = true)
    Resources resources;

    private final SingleSelectionModel<RequestStatistics> selectionModel = new
            SingleSelectionModel<RequestStatistics>();

    @Inject
    public FilterView(final Binder uiBinder, final UiHandlersStrategy<FilterUiHandlers> uiHandlersStrategy,
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
