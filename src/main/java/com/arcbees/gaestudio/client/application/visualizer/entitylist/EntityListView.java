package com.arcbees.gaestudio.client.application.visualizer.entitylist;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.shared.dto.entity.ParentKeyDTO;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntityListView extends ViewWithUiHandlers<EntityListUiHandlers> implements EntityListPresenter.MyView {

    public interface Binder extends UiBinder<Widget, EntityListView> {
    }

    private static final int PAGE_SIZE = 25;
    private static final Range DEFAULT_RANGE = new Range(0, PAGE_SIZE);
    private static final int NUMBER_OF_DEFAULT_COLUMNS = 2;

    @UiField
    HTMLPanel panel;
    @UiField
    SimplePager pager;
    @UiField
    CellTable<ParsedEntity> entityTable;
    @UiField
    Button refresh;

    private Set<String> currentProperties = new HashSet<String>();

    @Inject
    public EntityListView(final Binder uiBinder, final UiHandlersStrategy<EntityListUiHandlers> uiHandlersStrategy) {
        super(uiHandlersStrategy);

        initWidget(uiBinder.createAndBindUi(this));

        setSelectionModel();
        pager.setDisplay(entityTable);
        entityTable.setPageSize(PAGE_SIZE);
        pager.setPageSize(PAGE_SIZE);
        setDefaultColumns();
    }

    @Override
    public void setTableDataProvider(AsyncDataProvider<ParsedEntity> dataProvider) {
        dataProvider.addDataDisplay(entityTable);
    }

    @Override
    public void setRowCount(Integer count) {
        entityTable.setRowCount(count);
    }

    @Override
    public void setNewKind() {
        panel.setVisible(true);
        removeAllPropertyColumns();
        refresh.setVisible(true);
        entityTable.setVisibleRangeAndClearData(DEFAULT_RANGE, true);
    }

    @Override
    public void setData(Range range, List<ParsedEntity> entityEntities, Set<String> properties) {
        for (String property : properties) {
            if (!currentProperties.contains(property)) {
                entityTable.addColumn(new PropertyColumn(property), property);
                currentProperties.add(property);
            }
        }
        entityTable.setRowData(range.getStart(), entityEntities);
    }

    @UiHandler("refresh")
    void onRefreshClicked(ClickEvent event) {
        getUiHandlers().refreshData();
    }

    private void removeAllPropertyColumns() {
        while (entityTable.getColumnCount() != NUMBER_OF_DEFAULT_COLUMNS) {
            entityTable.removeColumn(NUMBER_OF_DEFAULT_COLUMNS);
        }
        currentProperties.clear();
    }

    private void setSelectionModel() {
        final SingleSelectionModel<ParsedEntity> selectionModel = new SingleSelectionModel<ParsedEntity>();
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                ParsedEntity selected = selectionModel.getSelectedObject();
                if (selected != null) {
                    getUiHandlers().onEntityClicked(selected);
                }
            }
        });
        entityTable.setSelectionModel(selectionModel);
    }

    private void setDefaultColumns() {
        TextColumn<ParsedEntity> idColumn = new TextColumn<ParsedEntity>() {
            @Override
            public String getValue(ParsedEntity EntityJsonParsed) {
                return EntityJsonParsed.getKey().getId().toString();
            }
        };
        entityTable.addColumn(idColumn, "ID");

        TextColumn<ParsedEntity> parentColumn = new TextColumn<ParsedEntity>() {
            @Override
            public String getValue(ParsedEntity EntityJsonParsed) {
                ParentKeyDTO parentKeyDTO = EntityJsonParsed.getKey().getParentKey();
                if (parentKeyDTO == null) {
                    return "<null>";
                }
                return parentKeyDTO.getKind() + ", " + parentKeyDTO.getId();
            }
        };
        entityTable.addColumn(parentColumn, "Parent");
    }

}
