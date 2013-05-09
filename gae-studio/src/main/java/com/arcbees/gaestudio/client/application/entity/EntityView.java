package com.arcbees.gaestudio.client.application.entity;

import java.util.List;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.resources.CellTableResource;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.view.client.Range;
import com.gwtplatform.mvp.client.ViewImpl;

public class EntityView extends ViewImpl implements EntityPresenter.MyView {
    interface Binder extends UiBinder<SimplePanel, EntityView> {
    }

    @UiField
    SimplePanel root;

    private final KeyValuePairBuilder keyValuePairBuilder;
    private final int pageSize = 15;
    private final CellTableResource cellTableResource;
    private CellTable<KeyValuePair> table;

    @Inject
    EntityView(Binder binder,
               KeyValuePairBuilder keyValuePairBuilder,
               CellTableResource cellTableResource) {
        this.keyValuePairBuilder = keyValuePairBuilder;
        this.cellTableResource = cellTableResource;

        initWidget(binder.createAndBindUi(this));

        initTable();
    }

    private void initTable() {
        TextColumn<KeyValuePair> keyColumn = new TextColumn<KeyValuePair>() {
            @Override
            public String getValue(KeyValuePair keyValuePair) {
                return keyValuePair.getKey();
            }
        };

        TextColumn<KeyValuePair> valueColumn = new TextColumn<KeyValuePair>() {
            @Override
            public String getValue(KeyValuePair keyValuePair) {
                return keyValuePair.getValue();
            }
        };

        table = new CellTable<KeyValuePair>(pageSize, cellTableResource);

        table.addColumn(keyColumn, "Key");
        table.addColumn(valueColumn, "Value");

        root.setWidget(table);
    }

    @Override
    public void showEntity(EntityDto entityDto) {
        ParsedEntity parsedEntity = new ParsedEntity(entityDto);

        List<KeyValuePair> keyValuePairs = keyValuePairBuilder.fromParsedEntity(parsedEntity);

        table.setVisibleRangeAndClearData(new Range(0, keyValuePairs.size()), false);
        table.setRowData(0, keyValuePairs);
    }
}
