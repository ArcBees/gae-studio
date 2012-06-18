package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.shared.dto.entity.EntityDTO;
import com.arcbees.gaestudio.shared.dto.entity.ParentKeyDTO;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.gwt.query.client.GQuery.$;

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

    private final NoSelectionModel<ParsedEntity> selectionModel = new NoSelectionModel<ParsedEntity>();
    private final Set<String> currentProperties = new HashSet<String>();

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
        entityTable.setVisibleRangeAndClearData(DEFAULT_RANGE, true);
    }

    @Override
    public void setData(Range range, List<ParsedEntity> parsedEntities) {
        Set<String> properties = new HashSet<String>();
        for (ParsedEntity parsedEntity : parsedEntities) {
            properties.addAll(parsedEntity.propertyKeys());
        }

        for (String property : properties) {
            if (!currentProperties.contains(property)) {
                entityTable.addColumn(new PropertyColumn(property), property);
                currentProperties.add(property);
            }
        }
        entityTable.setRowData(range.getStart(), parsedEntities);
    }

    public void redrawTable() {
        entityTable.redraw();
    }

    @Override
    public void addOrReplaceEntity(EntityDTO entityDTO) {
        List<ParsedEntity> visibleParsedEntities = entityTable.getVisibleItems();

        int rowIndex = 0;
        boolean isReplace = false;
        int i = 0;
        while (!isReplace && i < visibleParsedEntities.size()) {
            ParsedEntity parsedEntity = visibleParsedEntities.get(i);
            if (parsedEntity.getKey().getId().equals(entityDTO.getKey().getId())) {
                isReplace = true;
                parsedEntity.setEntityDTO(entityDTO);
                rowIndex = i;
                redrawTable();
            }

            i++;
        }

        if (!isReplace) {
            insertNewEntityAtTheTopOfTheCurrentPage(entityDTO, visibleParsedEntities);
        }

        highlightUpdatedOrInsertedLine(rowIndex);
    }

    private void insertNewEntityAtTheTopOfTheCurrentPage(EntityDTO entityDTO,
                                                         List<ParsedEntity> visibleParsedEntities) {
        List<ParsedEntity> newParsedEntities = new ArrayList<ParsedEntity>();
        ParsedEntity newParsedEntity = new ParsedEntity(entityDTO);
        newParsedEntities.add(newParsedEntity);
        newParsedEntities.addAll(visibleParsedEntities);

        setData(entityTable.getVisibleRange(), newParsedEntities);
    }

    private void highlightUpdatedOrInsertedLine(int rowIndex) {
        TableRowElement tableRowElement = entityTable.getRowElement(rowIndex);
        $(tableRowElement).animate("background-color: '#FFFF00'", 500).delay(500).animate("background-color: " +
                "'#FFFFFF'", 500);
    }

    private void removeAllPropertyColumns() {
        while (entityTable.getColumnCount() != NUMBER_OF_DEFAULT_COLUMNS) {
            entityTable.removeColumn(NUMBER_OF_DEFAULT_COLUMNS);
        }
        currentProperties.clear();
    }

    private void setSelectionModel() {
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                getUiHandlers().onEntityClicked(selectionModel.getLastSelectedObject());
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
