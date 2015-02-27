/**
 * Copyright 2015 ArcBees Inc.
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
