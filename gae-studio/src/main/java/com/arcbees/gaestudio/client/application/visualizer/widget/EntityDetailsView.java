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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class EntityDetailsView extends ViewWithUiHandlers<EntityDetailsUiHandlers>
        implements EntityDetailsPresenter.MyView {
    public interface Binder extends UiBinder<Widget, EntityDetailsView> {
    }

    @UiField
    TextArea entityDetails;
    @UiField
    Button save;
    @UiField
    PopupPanel popup;
    @UiField
    Button cancel;
    @UiField
    Label error;

    @Inject
    public EntityDetailsView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void displayEntityDetails(String json) {
        error.setVisible(false);
        popup.center();
        entityDetails.setText(json);
    }

    @Override
    public void hide() {
        popup.hide();
    }

    @Override
    public void showError(String message) {
        error.setVisible(true);
        error.setText(message);
    }

    @UiHandler("save")
    void onEditClicked(ClickEvent event) {
        getUiHandlers().saveEntity(entityDetails.getValue());
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        hide();
    }
}
