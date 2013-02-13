package com.arcbees.gaestudio.client.application.visualizer.widget;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.application.visualizer.ui.VisualizerUiFactory;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.ParentKeyDto;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

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
    InlineLabel entityName;

    private final VisualizerUiFactory visualizerUiFactory;
    private final SingleSelectionModel<ParsedEntity> selectionModel = new SingleSelectionModel<ParsedEntity>();
    private final Set<String> currentProperties = new HashSet<String>();

    @Inject
    public EntityListView(final Binder uiBinder, final VisualizerUiFactory visualizerUiFactory) {
        this.visualizerUiFactory = visualizerUiFactory;

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
    public void setNewKind(String currentKind) {
        panel.setVisible(true);
        removeAllPropertyColumns();
        entityTable.setVisibleRangeAndClearData(DEFAULT_RANGE, true);
        entityName.setText(currentKind);
    }

    @Override
    public void hideList() {
        panel.setVisible(false);
    }

    @Override
    public void setData(Range range, List<ParsedEntity> parsedEntities) {
        Set<String> properties = new HashSet<String>();
        for (ParsedEntity parsedEntity : parsedEntities) {
            properties.addAll(parsedEntity.propertyKeys());
        }

        for (String property : properties) {
            if (!currentProperties.contains(property)) {
                entityTable.addColumn(visualizerUiFactory.createPropertyColumn(property), property);
                currentProperties.add(property);
            }
        }
        entityTable.setRowData(range.getStart(), parsedEntities);
    }

    public void redrawTable() {
        entityTable.redraw();
    }

    @Override
    public void addOrReplaceEntity(EntityDto entityDTO) {
        int rowIndex = getRowIndex(entityDTO);

        if (rowIndex == -1) {
            insertNewEntityAtTheTopOfTheCurrentPage(entityDTO);
        } else {
            redrawTable();
        }
    }

    @Override
    public void removeEntity(EntityDto entityDTO) {
        int rowIndex = getRowIndex(entityDTO);

        if (rowIndex >= 0) {
            Range range = entityTable.getVisibleRange();
            entityTable.setVisibleRangeAndClearData(range, true);
        }
    }

    private int getRowIndex(EntityDto entityDTO) {
        List<ParsedEntity> visibleParsedEntities = entityTable.getVisibleItems();
        int rowIndex = -1;

        boolean isReplace = false;
        int i = 0;
        while (!isReplace && i < visibleParsedEntities.size()) {
            ParsedEntity parsedEntity = visibleParsedEntities.get(i);
            if (parsedEntity.getKey().getId().equals(entityDTO.getKey().getId())) {
                isReplace = true;
                rowIndex = i;
                parsedEntity.setEntityDTO(entityDTO);
            }

            i++;
        }

        return rowIndex;
    }

    private void insertNewEntityAtTheTopOfTheCurrentPage(EntityDto entityDTO) {
        ParsedEntity newParsedEntity = new ParsedEntity(entityDTO);

        List<ParsedEntity> newParsedEntities = new ArrayList<ParsedEntity>();
        newParsedEntities.add(newParsedEntity);
        // getVisibleItems return an unmodifiable list
        newParsedEntities.addAll(entityTable.getVisibleItems());
        Range range = entityTable.getVisibleRange();

        entityTable.setRowData(range.getStart(), newParsedEntities);
    }

    private void setSelectionModel() {
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                getUiHandlers().onEntitySelected(selectionModel.getSelectedObject());
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
                ParentKeyDto parentKeyDTO = EntityJsonParsed.getKey().getParentKey();
                if (parentKeyDTO == null) {
                    return "<null>";
                }
                return parentKeyDTO.getKind() + ", " + parentKeyDTO.getId();
            }
        };
        entityTable.addColumn(parentColumn, "Parent");
    }

    private void removeAllPropertyColumns() {
        while (entityTable.getColumnCount() != NUMBER_OF_DEFAULT_COLUMNS) {
            entityTable.removeColumn(NUMBER_OF_DEFAULT_COLUMNS);
        }
        currentProperties.clear();
    }

}
