/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.columnfilter;

import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class ColumnFilterView extends ViewWithUiHandlers<ColumnFilterUiHandlers>
        implements ColumnFilterPresenter.MyView {
    interface Binder extends UiBinder<HTMLPanel, ColumnFilterView> {
    }

    @UiField
    HTMLPanel list;
    @UiField
    Anchor showAll;
    @UiField
    Anchor hideAll;

    @Inject
    ColumnFilterView(Binder binder) {
        initWidget(binder.createAndBindUi(this));
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public void addToSlot(Object slot, IsWidget content) {
        if (slot == ColumnFilterPresenter.SLOT_COLUMNS) {
            list.add(content);
        }
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == ColumnFilterPresenter.SLOT_COLUMNS) {
            if (content == null) {
                list.clear();
            }
        }
    }

    @Override
    public void display(IsWidget isWidget) {
        list.add(isWidget);
    }

    @UiHandler("showAll")
    void onShowAll(ClickEvent event) {
        getUiHandlers().showAll();
    }

    @UiHandler("hideAll")
    void onHideAll(ClickEvent event) {
        getUiHandlers().hideAll();
    }
}
