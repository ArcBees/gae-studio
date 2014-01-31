/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.entity;

import java.util.List;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.client.resources.CellTableResource;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.Range;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import static com.google.gwt.query.client.GQuery.$;

public class EntityView extends ViewWithUiHandlers<EntityUiHandlers> implements EntityPresenter.MyView {
    interface Binder extends UiBinder<Widget, EntityView> {
    }

    @UiField
    SimplePanel root;
    @UiField
    HTMLPanel panelRoot;
    @UiField
    DivElement fullscreen;

    private final AppResources appResources;
    private final KeyValuePairBuilder keyValuePairBuilder;
    private final int pageSize = 15;
    private final CellTableResource cellTableResource;

    private CellTable<KeyValuePair> table;
    private boolean isFullscreen;

    @Inject
    EntityView(Binder binder,
               AppResources appResources,
               KeyValuePairBuilder keyValuePairBuilder,
               CellTableResource cellTableResource) {
        this.appResources = appResources;
        this.keyValuePairBuilder = keyValuePairBuilder;
        this.cellTableResource = cellTableResource;

        initWidget(binder.createAndBindUi(this));

        initTable();
    }

    @Override
    public void showEntity(EntityDto entityDto) {
        ParsedEntity parsedEntity = new ParsedEntity(entityDto);

        List<KeyValuePair> keyValuePairs = keyValuePairBuilder.fromParsedEntity(parsedEntity);

        table.setVisibleRangeAndClearData(new Range(0, keyValuePairs.size()), false);
        table.setRowData(0, keyValuePairs);
    }

    @Override
    public void bind() {
        $(fullscreen).click(new Function() {
            @Override
            public void f(Element e) {
                toggleFullScreenMode();
            }
        });
    }

    @Override
    public void resetFullScreen() {
        deactivateFullScreenMode();
    }

    private void toggleFullScreenMode() {
        if (isFullscreen) {
            deactivateFullScreenMode();
        } else {
            activateFullScreenMode();
        }
    }

    private void deactivateFullScreenMode() {
        getUiHandlers().deactivateFullScreen();
        isFullscreen = false;
        $(fullscreen).addClass(appResources.styles().expand());
        $(fullscreen).removeClass(appResources.styles().collapse());
    }

    private void activateFullScreenMode() {
        getUiHandlers().activateFullScreen();
        isFullscreen = true;
        $(fullscreen).removeClass(appResources.styles().expand());
        $(fullscreen).addClass(appResources.styles().collapse());
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
}
