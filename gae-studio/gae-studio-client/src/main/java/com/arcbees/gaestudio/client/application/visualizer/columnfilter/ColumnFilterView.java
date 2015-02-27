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
    ColumnFilterView(
                Binder binder) {
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
