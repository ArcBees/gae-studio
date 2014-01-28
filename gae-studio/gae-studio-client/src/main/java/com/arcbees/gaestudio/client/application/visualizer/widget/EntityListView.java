/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import java.util.ArrayList;
import java.util.List;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.client.resources.CellTableResource;
import com.arcbees.gaestudio.client.resources.PagerResources;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.Range;
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
    @UiField
    AnchorElement deselectEntity;

    private final String idStyleName;
    private final String lockedRowStyleName;
    private final String namespaceStyleName;
    private final String namespaceSpanStyleName;
    private final String pagerButtons;
    private final String firstTableRow;
    private final String entityStyleName;
    private final String secondTableStyleName;
    private final String secondTableHiddenStyleName;
    private final String kindStyleName;
    private final String firstTableStyleName;
    private final String pagerStyleName;
    private final ParsedEntityColumnCreator columnCreator;

    @Inject
    EntityListView(Binder uiBinder,
                   CellTableResource cellTableResource,
                   PagerResources pagerResources,
                   AppResources appResources,
                   ParsedEntityColumnCreator columnCreator) {
        this.columnCreator = columnCreator;
        pager = new SimplePager(SimplePager.TextLocation.CENTER, pagerResources, false, 1000, true);

        kindStyleName = appResources.styles().kindBold();
        idStyleName = appResources.styles().idBold();
        namespaceStyleName = appResources.styles().namespaceBold();
        namespaceSpanStyleName = appResources.styles().namespace();
        entityStyleName = appResources.styles().isDisplayingEntity();
        firstTableStyleName = appResources.styles().firstTable();
        pagerStyleName = appResources.styles().pager();
        lockedRowStyleName = appResources.styles().lockedRow();
        pagerButtons = "." + pagerStyleName + " tbody tr td img";
        firstTableRow = "." + firstTableStyleName + " tbody";
        secondTableStyleName = appResources.styles().secondTable();
        secondTableHiddenStyleName = appResources.styles().secondTableHidden();

        entityTable = new CellTable<ParsedEntity>(PAGE_SIZE, cellTableResource);
        entityTable.addAttachHandler(new AttachEvent.Handler() {
            @Override
            public void onAttachOrDetach(AttachEvent event) {
                onEditTableAttachedOrDetached(event.isAttached());
            }
        });

        initWidget(uiBinder.createAndBindUi(this));

        pager.setDisplay(entityTable);
        pager.setPageSize(PAGE_SIZE);

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
        $("." + kindStyleName).html(currentKind);
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
        while(entityTable.getColumnCount() > ParsedEntityColumnCreator.getDefaultColumnCount()) {
            removeLastColumn(entityTable);
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

        List<ParsedEntity> newParsedEntities = new ArrayList<ParsedEntity>();
        newParsedEntities.add(newParsedEntity);
        // getVisibleItems return an unmodifiable list
        newParsedEntities.addAll(entityTable.getVisibleItems());
        Range range = entityTable.getVisibleRange();

        entityTable.setRowData(range.getStart(), newParsedEntities);
    }

    private void onEditTableAttachedOrDetached(boolean attached) {
        if (attached) {
            bindGwtQuery();
        } else {
            $(firstTableRow).undelegate();
        }
    }

    private void bindGwtQuery() {
        $(firstTableRow).delegate("tr", BrowserEvents.CLICK, new Function() {
            @Override
            public void f(Element e) {
                if (!$(e).hasClass(lockedRowStyleName)) {
                    unlockRows();
                    lockRow(e);
                    initRightPanel(e);
                } else {
                    unlockRows();
                }
            }
        });

        $(pagerButtons).click(unlock);

        $(deselectEntity).click(unlock);
    }

    private Function unlock = new Function() {
        @Override
        public void f() {
            unlockRows();
            resetRightPanel();
        }
    };

    private ParsedEntity getParsedEntityForRow(Element element) {
        int absoluteRowIndex = Integer.valueOf($(element).attr("__gwt_row"));
        int pageStartIndex = entityTable.getVisibleRange().getStart();
        int relativeIndex = absoluteRowIndex - pageStartIndex;

        return entityTable.getVisibleItem(relativeIndex);
    }

    private void initRightPanel(Element e) {
        $("." + secondTableHiddenStyleName).removeClass(secondTableHiddenStyleName);

        ParsedEntity parsedEntity = getParsedEntityForRow(e);
        getUiHandlers().onEntitySelected(parsedEntity);

        KeyDto parseEntityKey = parsedEntity.getKey();
        $("." + idStyleName).text("ID " + parseEntityKey.getId());
        $("." + namespaceStyleName).text(parseEntityKey.getAppIdNamespace().getNamespace());

        if ($("." + namespaceStyleName).text().equals(ParsedEntityColumnCreator.IS_UNDEFINED)) {
            $("." + namespaceSpanStyleName).hide();
        } else {
            $("." + namespaceSpanStyleName).show();
        }
        $("." + secondTableStyleName).removeClass(secondTableHiddenStyleName);
    }

    private void resetRightPanel() {
        $("." + namespaceSpanStyleName).hide();
        $("." + entityStyleName).hide();
        $("." + idStyleName).text("no entity");
        $("." + secondTableStyleName).addClass(secondTableHiddenStyleName);
    }

    private void lockRow(Element e) {
        $(e).addClass(lockedRowStyleName);
        $(deselectEntity).show();
        getUiHandlers().onRowLock();
    }

    private void unlockRows() {
        $("." + lockedRowStyleName).removeClass(lockedRowStyleName);
        $(deselectEntity).hide();
        getUiHandlers().onRowUnlock();
    }
}
