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

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.gwtplatform.mvp.client.ViewImpl;

public class ColumnFilterView extends ViewImpl implements ColumnFilterPresenter.MyView {
    interface Binder extends UiBinder<HTMLPanel, ColumnFilterView> {
    }

    @UiField
    HTMLPanel list;

    @Inject
    ColumnFilterView(Binder binder) {
        initWidget(binder.createAndBindUi(this));
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public void display(IsWidget isWidget) {
        list.add(isWidget);
    }
}
