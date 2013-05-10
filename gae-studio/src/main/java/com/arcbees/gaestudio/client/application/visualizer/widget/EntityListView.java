/**
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import java.util.ArrayList;
import java.util.List;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.application.visualizer.ui.JsonContainer;
import com.arcbees.gaestudio.client.application.visualizer.ui.VisualizerUiFactory;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.client.resources.CellTableResource;
import com.arcbees.gaestudio.client.resources.EntityListTooltipResources;
import com.arcbees.gaestudio.client.resources.PagerResources;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.ParentKeyDto;
import com.arcbees.gquery.tooltip.client.Tooltip;
import com.arcbees.gquery.tooltip.client.TooltipOptions;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import static com.google.gwt.query.client.GQuery.$;

public class EntityListView extends ViewWithUiHandlers<EntityListUiHandlers> implements EntityListPresenter.MyView {
    interface Binder extends UiBinder<Widget, EntityListView> {
    }

    private static final int PAGE_SIZE = 25;
    private static final Range DEFAULT_RANGE = new Range(0, PAGE_SIZE);

    @UiField
    HTMLPanel panel;
    @UiField(provided = true)
    SimplePager pager;
    @UiField(provided = true)
    CellTable<ParsedEntity> entityTable;

    private final VisualizerUiFactory visualizerUiFactory;
    private final EntityListTooltipResources entityListTooltipResources;
    private final SingleSelectionModel<ParsedEntity> selectionModel = new SingleSelectionModel<ParsedEntity>();
    private final String firstTableStyleName;
    private final String secondTableStyleName;
    private final String secondTableFixStyleName;
    private final String pagerStyleName;
    private final String pagerFixStyleName;

    private Tooltip tooltip;

    @Inject
    EntityListView(Binder uiBinder,
                   CellTableResource cellTableResource,
                   VisualizerUiFactory visualizerUiFactory,
                   EntityListTooltipResources entityListTooltipResources,
                   PagerResources pagerResources,
                   AppResources appResources) {
        pager = new SimplePager(SimplePager.TextLocation.CENTER, pagerResources, false, 1000, true);
        this.visualizerUiFactory = visualizerUiFactory;
        this.entityListTooltipResources = entityListTooltipResources;

        this.firstTableStyleName = appResources.styles().firstTable();
        this.secondTableStyleName = appResources.styles().secondTable();
        this.secondTableFixStyleName = appResources.styles().secondTableFix();
        this.pagerStyleName = appResources.styles().pager();
        this.pagerFixStyleName = appResources.styles().pagerFix();

        entityTable = new CellTable<ParsedEntity>(PAGE_SIZE, cellTableResource);
        entityTable.addAttachHandler(new AttachEvent.Handler() {
            @Override
            public void onAttachOrDetach(AttachEvent event) {
                onEditTableAttachedOrDetached(event.isAttached());
            }
        });

        initWidget(uiBinder.createAndBindUi(this));

        setSelectionModel();
        pager.setDisplay(entityTable);
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
        entityTable.setVisibleRangeAndClearData(DEFAULT_RANGE, true);
    }

    @Override
    public void hideList() {
        panel.setVisible(false);
    }

    @Override
    public void setData(Range range, List<ParsedEntity> parsedEntities) {
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
            public String getValue(ParsedEntity entityJsonParsed) {
                return entityJsonParsed.getKey().getId().toString();
            }
        };
        entityTable.addColumn(idColumn, "ID");

        TextColumn<ParsedEntity> parentKindColumn = new TextColumn<ParsedEntity>() {
            @Override
            public String getValue(ParsedEntity entityJsonParsed) {
                ParentKeyDto parentKeyDTO = entityJsonParsed.getKey().getParentKey();
                if (parentKeyDTO == null) {
                    return "<null>";
                }
                return parentKeyDTO.getKind();
            }
        };
        entityTable.addColumn(parentKindColumn, "Parent Kind");

        TextColumn<ParsedEntity> parentIdColumn = new TextColumn<ParsedEntity>() {
            @Override
            public String getValue(ParsedEntity entityJsonParsed) {
                ParentKeyDto parentKeyDTO = entityJsonParsed.getKey().getParentKey();
                if (parentKeyDTO == null) {
                    return "<null>";
                }
                return parentKeyDTO.getId().toString();
            }
        };
        entityTable.addColumn(parentIdColumn, "Parent ID");
    }

    private void onEditTableAttachedOrDetached(boolean attached) {
        if (attached) {
            initTooltip();
        } else {
            detachTooltip();
        }
    }

    private void detachTooltip() {
        tooltip.destroy();
    }

    private void initTooltip() {
        TooltipOptions options = new TooltipOptions()
                .withTrigger(TooltipOptions.TooltipTrigger.HOVER)
                .withSelector("tbody tr")
                .withResources(entityListTooltipResources)
                .withContainer("element")
                .withPlacement(TooltipOptions.TooltipPlacement.RIGHT)
                .withAnimation(false)
                .withContent(new TooltipOptions.TooltipWidgetContentProvider() {
                    @Override
                    public IsWidget getContent(Element element) {
                        return createEntityContent(element);
                    }
                });

        tooltip = $(entityTable).as(Tooltip.Tooltip).tooltip(options);
    }

    private void bindGwtQuery() {
        $("." + firstTableStyleName + " tbody tr").hover(new Function() {
               @Override
               public void f() {
                   $("." + pagerStyleName).addClass(pagerFixStyleName);
                   $("." + secondTableStyleName).addClass(secondTableFixStyleName);
               }
           }, new Function() {
               @Override
               public void f() {
                   $("." + pagerStyleName).removeClass(pagerFixStyleName);
                   $("." + secondTableStyleName).removeClass(secondTableFixStyleName);
               }
           }
        );
    }

    private IsWidget createEntityContent(Element element) {
        int absoluteRowIndex = Integer.valueOf($(element).attr("__gwt_row"));
        int pageStartIndex = entityTable.getVisibleRange().getStart();
        int relativeIndex = absoluteRowIndex - pageStartIndex;

        ParsedEntity parsedEntity = entityTable.getVisibleItem(relativeIndex);
        JsonContainer container = visualizerUiFactory.createJsonContainer(parsedEntity.getJson());
        container.addAttachHandler(container);
        bindGwtQuery();
        return container;
    }
}
