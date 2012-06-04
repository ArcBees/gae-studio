package com.arcbees.gaestudio.client.application.visualizer.entitylist;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.shared.dto.entity.EntityDTO;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;

public class EntityListView extends ViewWithUiHandlers<EntityListUiHandlers> implements EntityListPresenter.MyView {

    public interface Binder extends UiBinder<Widget, EntityListView> {
    }

    private static final int PAGE_SIZE = 10;
    private static final Range DEFAULT_RANGE = new Range(0, PAGE_SIZE);

    @UiField
    HTMLPanel panel;
    @UiField
    SimplePager pager;
    @UiField
    CellTable<EntityDTO> entityTable;
    @UiField
    Button refresh;

    @Inject
    public EntityListView(final Binder uiBinder, final UiHandlersStrategy<EntityListUiHandlers> uiHandlersStrategy) {
        super(uiHandlersStrategy);

        initWidget(uiBinder.createAndBindUi(this));

        setColumns();
        setSelectionModel();
        pager.setDisplay(entityTable);
        entityTable.setPageSize(PAGE_SIZE);
        pager.setPageSize(PAGE_SIZE);
    }

    @Override
    public void setTableDataProvider(AsyncDataProvider<EntityDTO> dataProvider) {
        dataProvider.addDataDisplay(entityTable);
    }

    @Override
    public void setRowCount(Integer count) {
        entityTable.setRowCount(count, false);
    }

    @Override
    public void setNewKind() {
        refresh.setVisible(true);
        entityTable.setVisibleRangeAndClearData(DEFAULT_RANGE, true);
    }

    @UiHandler("refresh")
    void onRefreshClicked(ClickEvent event) {
        getUiHandlers().refreshData();
    }

    private void setSelectionModel() {
        final SingleSelectionModel<EntityDTO> selectionModel = new SingleSelectionModel<EntityDTO>();
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                EntityDTO selected = selectionModel.getSelectedObject();
                if (selected != null) {
                    getUiHandlers().onEntityClicked(selected);
                }
            }
        });
        entityTable.setSelectionModel(selectionModel);
    }

    private void setColumns() {
        TextColumn<EntityDTO> idColumn = new TextColumn<EntityDTO>() {
            @Override
            public String getValue(EntityDTO entityDTO) {
                return entityDTO.getKey().getId().toString();
            }
        };
        entityTable.addColumn(idColumn, "ID");

        TextColumn<EntityDTO> descriptionColumn = new TextColumn<EntityDTO>() {
            @Override
            public String getValue(EntityDTO entityDTO) {
                return entityDTO.toString();
            }
        };
        entityTable.addColumn(descriptionColumn);
    }

}
