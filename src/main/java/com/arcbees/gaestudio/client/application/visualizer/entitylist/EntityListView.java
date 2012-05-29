package com.arcbees.gaestudio.client.application.visualizer.entitylist;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.ui.BaseLabel;
import com.arcbees.gaestudio.client.application.ui.LabelCallback;
import com.arcbees.gaestudio.client.application.ui.SelectableLabelServant;
import com.arcbees.gaestudio.client.application.visualizer.VisualizerLabelFactory;
import com.arcbees.gaestudio.shared.dto.entity.EntityDTO;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.*;
import com.google.inject.Inject;

import java.util.ArrayList;

public class EntityListView extends ViewWithUiHandlers<EntityListUiHandlers> implements EntityListPresenter.MyView {

    public interface Binder extends UiBinder<Widget, EntityListView> {
    }

    private static final Range DEFAULT_RANGE = new Range(0, 15);

    @UiField
    HTMLPanel panel;
    @UiField
    SimplePager pager;
    @UiField
    CellTable<EntityDTO> entityTable;

    @Inject
    public EntityListView(final Binder uiBinder, final UiHandlersStrategy<EntityListUiHandlers> uiHandlersStrategy) {
        super(uiHandlersStrategy);

        initWidget(uiBinder.createAndBindUi(this));

        setColumns();
        setSelectionModel();
        pager.setDisplay(entityTable);
    }

    @Override
    public void setTableDataProvider(AsyncDataProvider<EntityDTO> dataProvider) {
        dataProvider.addDataDisplay(entityTable);
    }

    @Override
    public void setNewKind() {
        entityTable.setVisibleRangeAndClearData(DEFAULT_RANGE, true);
    }

    private void setSelectionModel() {
        final SingleSelectionModel<EntityDTO> selectionModel = new SingleSelectionModel<EntityDTO>();
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                EntityDTO selected = selectionModel.getSelectedObject();
                if (selected != null) {
                    getUiHandlers().onEntityClicked(selected.getKey(), selected.getJson());
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
