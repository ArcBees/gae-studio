package com.arcbees.gaestudio.client.application.visualizer.kind;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.Resources;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;

import java.util.List;

public class KindListView extends ViewWithUiHandlers<KindListUiHandlers> implements KindListPresenter.MyView {

    public interface Binder extends UiBinder<Widget, KindListView> {
    }

    @UiField(provided = true)
    CellList<String> kinds = new CellList<String>(new TextCell());

    @UiField(provided = true)
    Resources resources;

    private final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();

    @Inject
    public KindListView(final Binder uiBinder, final UiHandlersStrategy<KindListUiHandlers> uiHandlersStrategy,
                        final Resources resources) {
        super(uiHandlersStrategy);

        this.resources = resources;
        initWidget(uiBinder.createAndBindUi(this));

        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                getUiHandlers().onKindClicked(selectionModel.getSelectedObject());
            }
        });
        kinds.setSelectionModel(selectionModel);
    }

    @Override
    public void updateKinds(List<String> kinds) {
        this.kinds.setRowData(kinds);
    }

}
