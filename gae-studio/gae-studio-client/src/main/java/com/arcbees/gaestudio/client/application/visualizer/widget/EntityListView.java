/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import java.util.ArrayList;
import java.util.List;

import com.arcbees.analytics.client.universalanalytics.UniversalAnalytics;
import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.client.resources.CellTableResource;
import com.arcbees.gaestudio.client.resources.PagerResources;
import com.arcbees.gaestudio.client.resources.VisualizerResources;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.RowCountChangeEvent;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import static com.arcbees.gaestudio.client.application.analytics.EventCategories.UI_ELEMENTS;
import static com.google.gwt.query.client.GQuery.$;

public class EntityListView extends ViewWithUiHandlers<EntityListUiHandlers> implements EntityListPresenter.MyView,
        CellPreviewEvent.Handler<ParsedEntity> {
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
    @UiField
    DivElement refresh;
    @UiField
    DivElement byGql;
    @UiField
    TextArea formQuery;
    @UiField
    DivElement formQueryHolder;
    @UiField
    SimplePanel deleteByKind;
    @UiField
    DivElement deselect;
    @UiField
    Button runQueryButton;

    private final AppResources appResources;
    private final VisualizerResources visualizerResources;
    private final String pagerButtons;
    private final ParsedEntityColumnCreator columnCreator;
    private final MultiSelectionModel<ParsedEntity> selectionModel;
    private final Function unlock = new Function() {
        @Override
        public void f() {
            unselectRows();
        }
    };
    private final UniversalAnalytics universalAnalytics;

    private HandlerRegistration firstLoadHandlerRegistration;
    private boolean gwtBound = false;

    @Inject
    EntityListView(Binder uiBinder,
                   CellTableResource cellTableResource,
                   PagerResources pagerResources,
                   AppResources appResources,
                   VisualizerResources visualizerResources,
                   ParsedEntityColumnCreator columnCreator,
                   UniversalAnalytics universalAnalytics) {
        this.columnCreator = columnCreator;
        this.appResources = appResources;
        this.visualizerResources = visualizerResources;
        this.universalAnalytics = universalAnalytics;

        pager = new SimplePager(SimplePager.TextLocation.CENTER, pagerResources, false, 1000, true);

        pagerButtons = "." + appResources.styles().pager() + " tbody tr td img";

        entityTable = new CellTable<>(PAGE_SIZE, cellTableResource);
        entityTable.addAttachHandler(new AttachEvent.Handler() {
            @Override
            public void onAttachOrDetach(AttachEvent event) {
                onEditTableAttachedOrDetached(event.isAttached());
            }
        });

        selectionModel = new MultiSelectionModel<>();
        entityTable.setSelectionModel(selectionModel, this);

        initWidget(uiBinder.createAndBindUi(this));

        pager.setPageSize(PAGE_SIZE);
        pager.setDisplay(entityTable);

        columnCreator.initializeTable(entityTable);
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

    @Override
    public void addProperty(String propertyName) {
        columnCreator.addPropertyColumn(entityTable, propertyName);
    }

    @Override
    public void redraw() {
        entityTable.redraw();
    }

    @Override
    public void removeKindSpecificColumns() {
        while (entityTable.getColumnCount() > ParsedEntityColumnCreator.getDefaultColumnCount()) {
            removeLastColumn(entityTable);
        }
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (EntityListPresenter.SLOT_NAMESPACES == slot) {
            deleteByKind.setWidget(content);
        }
    }

    @Override
    public void unselectRows() {
        selectionModel.clear();

        AppResources.Styles styles = appResources.styles();
        $(deselect).removeClass(styles.deselect())
                .addClass(styles.deselectDisabled())
                .unbind(BrowserEvents.CLICK, unlock);

        getUiHandlers().onRowUnlock();
    }

    @Override
    public void setRowSelected(final String encodedKey) {
        if (!entityTable.getLoadingIndicator().isVisible()) {
            $(entityTable).delay(1, new Function() {
                @Override
                public void f() {
                    doSetRowSelected(encodedKey);
                }
            });
        } else {
            final RowCountChangeEvent.Handler handler = new RowCountChangeEvent.Handler() {
                @Override
                public void onRowCountChange(RowCountChangeEvent event) {
                    doSetRowSelected(encodedKey);
                    firstLoadHandlerRegistration.removeHandler();
                }
            };
            firstLoadHandlerRegistration = entityTable.addRowCountChangeHandler(handler);
        }
    }

    @Override
    public void onCellPreview(CellPreviewEvent<ParsedEntity> event) {
        if (BrowserEvents.CLICK.equals(event.getNativeEvent().getType())) {
            ParsedEntity parsedEntity = event.getValue();
            if (event.getNativeEvent().getCtrlKey() || event.getNativeEvent().getShiftKey()) {
                if (selectionModel.isSelected(parsedEntity)) {
                    unselectRow(parsedEntity);
                } else {
                    selectRow(parsedEntity);
                }
            } else {
                if (selectionModel.isSelected(parsedEntity)) {
                    unselectRows();
                } else {
                    unselectRows();
                    selectRow(parsedEntity);
                }
            }
        }
    }

    @UiHandler("runQueryButton")
    public void runGqlQuery(ClickEvent event) {
        getUiHandlers().runGqlQuery(formQuery.getText());

        universalAnalytics.sendEvent(UI_ELEMENTS, "click").eventLabel("Visualizer -> List View -> Run GQL Query");
    }

    private void doSetRowSelected(String encodedKey) {
        for (int i = 0; i < entityTable.getVisibleItems().size(); i++) {
            ParsedEntity parsedEntity = entityTable.getVisibleItem(i);
            KeyDto key = parsedEntity.getKey();
            if (key.getEncodedKey().equals(encodedKey)) {
                selectRow(parsedEntity);
                return;
            }
        }
    }

    private void removeLastColumn(CellTable<ParsedEntity> entityTable) {
        int lastColumnIndex = entityTable.getColumnCount() - 1;

        entityTable.removeColumn(lastColumnIndex);
    }

    private void redrawTable() {
        entityTable.redraw();
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
                parsedEntity.setEntityDto(entityDTO);
            }

            i++;
        }

        return rowIndex;
    }

    private void insertNewEntityAtTheTopOfTheCurrentPage(EntityDto entityDTO) {
        ParsedEntity newParsedEntity = new ParsedEntity(entityDTO);

        List<ParsedEntity> newParsedEntities = new ArrayList<>();
        newParsedEntities.add(newParsedEntity);
        // getVisibleItems return an unmodifiable list
        newParsedEntities.addAll(entityTable.getVisibleItems());
        Range range = entityTable.getVisibleRange();

        entityTable.setRowData(range.getStart(), newParsedEntities);
    }

    private void onEditTableAttachedOrDetached(boolean attached) {
        if (attached && !gwtBound) {
            bindGwtQuery();
            gwtBound = true;
        }
    }

    private void bindGwtQuery() {
        $(pagerButtons).click(unlock);

        $(refresh).click(new Function() {
            @Override
            public void f() {
                getUiHandlers().refresh();
                unselectRows();
                universalAnalytics.sendEvent(UI_ELEMENTS, "click").eventLabel("Visualizer -> List View -> Refresh");
            }
        });

        $(byGql).click(new Function() {
            @Override
            public void f() {
                toggleGQL();
            }
        });

        $(formQueryHolder).slideUp(0);
    }

    private void selectRow(ParsedEntity parsedEntity) {
        AppResources.Styles styles = appResources.styles();
        if ($(deselect).hasClass(styles.deselectDisabled())) {
            $(deselect).unbind(BrowserEvents.CLICK)
                    .click(unlock, new Function() {
                                @Override
                                public void f() {
                                    universalAnalytics.sendEvent(UI_ELEMENTS, "click")
                                            .eventLabel("Visualizer -> List View -> Deselect Entity");
                                }})
                    .addClass(styles.deselect())
                    .removeClass(styles.deselectDisabled());
        }

        selectionModel.setSelected(parsedEntity, true);

        getUiHandlers().onEntitySelected(selectionModel.getSelectedSet());
        
        universalAnalytics.sendEvent(UI_ELEMENTS, "select").eventLabel("Visualizer -> List View -> Entity Row");
    }

    private void unselectRow(ParsedEntity parsedEntity) {
        selectionModel.setSelected(parsedEntity, false);

        if (selectionModel.getSelectedSet().isEmpty()) {
            unselectRows();
        } else {
            getUiHandlers().onEntitySelected(selectionModel.getSelectedSet());
        }
    }

    private void toggleGQL() {
        VisualizerResources.EntityList styles = visualizerResources.entityList();
        $(byGql).toggleClass(styles.open());

        if ($(byGql).hasClass(styles.open())) {
            $(formQueryHolder).slideDown(100);
            universalAnalytics.sendEvent(UI_ELEMENTS, "open")
                    .eventLabel("Visualizer -> List View -> GQL Query Textarea");
        } else {
            $(formQueryHolder).slideUp(100);

            universalAnalytics.sendEvent(UI_ELEMENTS, "close")
                    .eventLabel("Visualizer -> List View -> GQL Query Textarea");
        }
    }
}
