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

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class ColumnNameView extends ViewWithUiHandlers<ColumnNameUiHandlers>
        implements ColumnNamePresenter.MyView {
    interface Binder extends UiBinder<Widget, ColumnNameView> {
    }

    @UiField
    CheckBox checkBox;

    @Inject
    ColumnNameView(
            Binder binder) {
        initWidget(binder.createAndBindUi(this));
    }

    @UiHandler("checkBox")
    public void onValueChange(ValueChangeEvent<Boolean> event) {
        getUiHandlers().onValueChange(event.getValue());
    }

    @Override
    public void setColumnName(String columnName) {
        checkBox.setText(columnName);
    }

    @Override
    public void setChecked(boolean checked) {
        checkBox.setValue(checked);
    }
}
