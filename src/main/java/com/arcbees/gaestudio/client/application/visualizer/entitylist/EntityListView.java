package com.arcbees.gaestudio.client.application.visualizer.entitylist;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.domain.EntityJsonParsed;
import com.arcbees.gaestudio.shared.dto.entity.ParentKeyDTO;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
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

    private static final int PAGE_SIZE = 10;
    private static final Range DEFAULT_RANGE = new Range(0, PAGE_SIZE);
    private static final int NUMBER_OF_DEFAULT_COLUMNS = 2;
    private static final String NULL_TAG = "<null>";
    private static final String MISSING_TAG = "<missing>";

    @UiField
    HTMLPanel panel;
    @UiField
    SimplePager pager;
    @UiField
    CellTable<EntityJsonParsed> entityTable;
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
    public void setTableDataProvider(AsyncDataProvider<EntityJsonParsed> dataProvider) {
        dataProvider.addDataDisplay(entityTable);
    }

    @Override
    public void setRowCount(Integer count) {
        entityTable.setRowCount(count);
    }

    @Override
    public void setNewKind() {
        removeAllPropertyColumns();
        refresh.setVisible(true);
        entityTable.setVisibleRangeAndClearData(DEFAULT_RANGE, true);
    }

    @Override
    public void setData(Range range, List<EntityJsonParsed> entities, Set<String> properties) {
        for (String property : properties) {
            if (!currentProperties.contains(property)) {
                entityTable.addColumn(createColumnProperty(property), property);
                currentProperties.add(property);
            }
        }
        entityTable.setRowData(range.getStart(), entities);
    }

    @UiHandler("refresh")
    void onRefreshClicked(ClickEvent event) {
        getUiHandlers().refreshData();
    }

    private TextColumn<EntityJsonParsed> createColumnProperty(final String property) {
        return new TextColumn<EntityJsonParsed>() {
            @Override
            public String getValue(EntityJsonParsed entityJsonParsed) {
                if (entityJsonParsed.hasProperty(property)) {
                    JSONValue value = entityJsonParsed.getProperty(property);
                    if (value == null) {
                        return NULL_TAG;
                    } else if (value.isObject() != null) {
                        JSONObject object = value.isObject();
                        if (object.containsKey("kind") && object.containsKey("id")) {
                            return object.get("kind").isString().stringValue() + ", " + object.get("id");
                        }
                    } else if (value.isString() != null) {
                        return value.isString().stringValue();
                    }
                    return value.toString();
                } else {
                    return MISSING_TAG;
                }
            }
        };
    }

    private void removeAllPropertyColumns() {
        while (entityTable.getColumnCount() != NUMBER_OF_DEFAULT_COLUMNS) {
            entityTable.removeColumn(NUMBER_OF_DEFAULT_COLUMNS);
        }
        currentProperties.clear();
    }

    private void setSelectionModel() {
        final SingleSelectionModel<EntityJsonParsed> selectionModel = new SingleSelectionModel<EntityJsonParsed>();
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                EntityJsonParsed selected = selectionModel.getSelectedObject();
                if (selected != null) {
                    getUiHandlers().onEntityClicked(selected);
                }
            }
        });
        entityTable.setSelectionModel(selectionModel);
    }

    private void setDefaultColumns() {
        TextColumn<EntityJsonParsed> idColumn = new TextColumn<EntityJsonParsed>() {
            @Override
            public String getValue(EntityJsonParsed EntityJsonParsed) {
                return EntityJsonParsed.getKey().getId().toString();
            }
        };
        entityTable.addColumn(idColumn, "ID");

        TextColumn<EntityJsonParsed> parentColumn = new TextColumn<EntityJsonParsed>() {
            @Override
            public String getValue(EntityJsonParsed EntityJsonParsed) {
                ParentKeyDTO parentKeyDTO = EntityJsonParsed.getKey().getParentKey();
                if (parentKeyDTO == null) {
                    return NULL_TAG;
                }
                return parentKeyDTO.getKind() + ", " + parentKeyDTO.getId();
            }
        };
        entityTable.addColumn(parentColumn, "Parent");
    }

}
