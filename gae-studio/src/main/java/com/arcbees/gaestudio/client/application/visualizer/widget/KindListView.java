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

import java.util.List;

import com.arcbees.gaestudio.client.resources.AppConstants;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class KindListView extends ViewWithUiHandlers<KindListUiHandlers> implements KindListPresenter.MyView,
        ChangeHandler {
    public interface Binder extends UiBinder<Widget, KindListView> {
    }

    @UiField
    ListBox kinds;

    private final AppConstants myConstants;

    @Inject
    public KindListView(final Binder uiBinder, final AppConstants myConstants) {
        this.myConstants = myConstants;

        initWidget(uiBinder.createAndBindUi(this));

        kinds.addChangeHandler(this);
    }

    @Override
    public void onChange(ChangeEvent event) {
        String selectedKind = kinds.getValue(kinds.getSelectedIndex());

        getUiHandlers().onKindClicked(selectedKind);
    }

    @Override
    public void updateKinds(List<String> kinds) {
        clearEntityList();

        for (String kind : kinds) {
            this.kinds.addItem(kind);
        }
    }

    private void clearEntityList() {
        kinds.clear();
        kinds.addItem(myConstants.chooseAnEntity(), "");
    }
}
