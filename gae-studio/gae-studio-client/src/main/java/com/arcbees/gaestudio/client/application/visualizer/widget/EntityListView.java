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
import com.arcbees.gaestudio.client.application.visualizer.ui.VisualizerUiFactory;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.client.resources.CellTableResource;
import com.arcbees.gaestudio.client.resources.PagerResources;
import com.arcbees.gaestudio.client.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.client.dto.entity.EntityDto;
import com.arcbees.gaestudio.client.dto.entity.KeyDto;
import com.arcbees.gaestudio.client.dto.entity.ParentKeyDto;
import com.arcbees.gquery.tooltip.client.Tooltip;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.Range;
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
    private final SingleSelectionModel<ParsedEntity> selectionModel = new SingleSelectionModel<ParsedEntity>();
    private final String idStyleName;
    private final String lockedRowStyleName;
    private final String namespaceStyleName;
    private final String namespaceSpanStyleName;
    private final String pagerButtons;
    private final String unlockButton;
    private final String extendButton;
    private final String backButton;
    private final String firstTableRow;
    private final String entityStyleName;
    private final String secondTableStyleName;
    private final String secondTableHiddenStyleName;
    private final String entityContainerStyleName;
    private final String entityListContainerSelectedStyleName;
    private final String kindStyleName;
    private final String firstTableStyleName;
    private final String pagerStyleName;
    private final String isNull = "<null>";
    private final String isUndefined = "";


    private Tooltip tooltip;

    @Inject
    EntityListView(Binder uiBinder,
                   CellTableResource cellTableResource,
                   VisualizerUiFactory visualizerUiFactory,
                   PagerResources pagerResources,
                   AppResources appResources) {
        pager = new SimplePager(SimplePager.TextLocation.CENTER, pagerResources, false, 1000, true);
        this.visualizerUiFactory = visualizerUiFactory;

        kindStyleName = appResources.styles().kindBold();
        idStyleName = appResources.styles().idBold();
        namespaceStyleName = appResources.styles().namespaceBold();
        namespaceSpanStyleName = appResources.styles().namespace();
        entityStyleName = appResources.styles().isDisplayingEntity();
        firstTableStyleName = appResources.styles().firstTable();
        pagerStyleName = appResources.styles().pager();
        lockedRowStyleName = appResources.styles().lockedRow();
        pagerButtons = "." + pagerStyleName + " tbody tr td img";
        unlockButton = "." + appResources.styles().unlockButton();
        extendButton = "." + appResources.styles().fullscreenButton();
        backButton = "." + appResources.styles().backButton();
        firstTableRow = "." + firstTableStyleName + " tbody";
        secondTableStyleName = appResources.styles().secondTable();
        secondTableHiddenStyleName = appResources.styles().secondTableHidden();
        entityListContainerSelectedStyleName = appResources.styles().entityListContainerSelected();
        entityContainerStyleName = appResources.styles().entityContainer();

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
                    return isNull;
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
                    return isNull;
                }
                return parentKeyDTO.getId().toString();
            }
        };
        entityTable.addColumn(parentIdColumn, "Parent ID");

        TextColumn<ParsedEntity> namespaceColumn = new TextColumn<ParsedEntity>() {
            @Override
            public String getValue(ParsedEntity entityJsonParsed) {
                KeyDto keyDto = entityJsonParsed.getKey();
                AppIdNamespaceDto appIdNamespaceDto = keyDto.getAppIdNamespaceDto();
                String namespace = appIdNamespaceDto.getNamespace();
                if (namespace == null) {
                    namespace = isNull;
                } else if (namespace.isEmpty()) {
                    namespace = isUndefined;
                }
                return namespace;
            }
        };
        entityTable.addColumn(namespaceColumn, "Namespace");
    }

    private void onEditTableAttachedOrDetached(boolean attached) {
        if (attached) {
            bindGwtQuery();
        } else {
            $(firstTableRow).undelegate();
        }
    }

    private void bindGwtQuery() {
        $(firstTableRow).delegate("tr", BrowserEvents.MOUSEOVER, new Function() {
            @Override
            public void f(Element e) {
                if ($("." + lockedRowStyleName).isEmpty()) {
                    initRightPanel(e);
                }
            }
        });

        $(firstTableRow).delegate("tr", BrowserEvents.MOUSEOUT, new Function() {
            @Override
            public void f() {
                if ($("." + lockedRowStyleName).isEmpty()) {
                    resetRightPanel();
                }
            }
        });

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

        $(pagerButtons).click(new Function() {
            @Override
            public void f() {
                unlockRows();
                resetRightPanel();
            }
        });

        $(unlockButton).click(new Function() {
            @Override
            public void f() {
                unlockRows();
                resetRightPanel();
            }
        });

        $(backButton).click(new Function() {
            @Override
            public void f() {
                $("." + entityContainerStyleName).removeClass(entityListContainerSelectedStyleName);
                $(extendButton).show();
                $(backButton).hide();
            }
        });

        $(extendButton).click(new Function() {
            @Override
            public void f(Element e) {
                $("." + entityContainerStyleName).addClass(entityListContainerSelectedStyleName);
                $(extendButton).hide();
                $(backButton).show();
            }
        });
    }

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
        $("." + namespaceStyleName).text(parseEntityKey.getAppIdNamespaceDto().getNamespace());

        if ($("." + namespaceStyleName).text().equals(isUndefined)) {
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
        $(unlockButton).show();
        $(extendButton).show();
    }

    private void unlockRows() {
        $("." + lockedRowStyleName).removeClass(lockedRowStyleName);
        $(unlockButton).hide();
        $(extendButton).hide();
    }
}
