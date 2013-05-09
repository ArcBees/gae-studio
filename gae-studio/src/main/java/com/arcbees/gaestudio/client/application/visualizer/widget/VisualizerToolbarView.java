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

import com.arcbees.gaestudio.client.application.ui.ToolbarButton;
import com.arcbees.gaestudio.client.application.ui.ToolbarButtonCallback;
import com.arcbees.gaestudio.client.application.ui.UiFactory;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class VisualizerToolbarView extends ViewWithUiHandlers<VisualizerToolbarUiHandlers> implements
        VisualizerToolbarPresenter.MyView {
    interface Binder extends UiBinder<Widget, VisualizerToolbarView> {
    }

    @UiField(provided = true)
    AppResources resources;
    @UiField
    HTMLPanel buttons;

    private final UiFactory uiFactory;
    private final AppConstants myConstants;
    private final ToolbarButton refresh;
    private final ToolbarButton create;
    private final ToolbarButton edit;
    private final ToolbarButton delete;

    @Inject
    VisualizerToolbarView(Binder uiBinder,
                          AppResources resources,
                          UiFactory uiFactory,
                          AppConstants myConstants) {
        this.resources = resources;
        this.uiFactory = uiFactory;
        this.myConstants = myConstants;

        initWidget(uiBinder.createAndBindUi(this));

        refresh = createRefreshButton();
        create = createCreateButton();
        edit = createEditButton();
        delete = createDeleteButton();

        buttons.add(refresh);
        buttons.add(create);
        buttons.add(edit);
        buttons.add(delete);

        edit.setEnabled(false);
        delete.setEnabled(false);
    }

    @Override
    public void setKindSelected(boolean isSelected) {
        buttons.setVisible(isSelected);
    }

    @Override
    public void enableContextualMenu() {
        edit.setEnabled(true);
        delete.setEnabled(true);
    }

    @Override
    public void disableContextualMenu() {
        edit.setEnabled(false);
        delete.setEnabled(false);
    }

    private ToolbarButton createRefreshButton() {
        return uiFactory.createToolbarButton(myConstants.refresh(), resources.refresh(), new ToolbarButtonCallback() {
            @Override
            public void onClicked() {
                getUiHandlers().refresh();
            }
        });
    }

    private ToolbarButton createCreateButton() {
        return uiFactory.createToolbarButton(myConstants.create(), resources.create(), new ToolbarButtonCallback() {
            @Override
            public void onClicked() {
                getUiHandlers().create();
            }
        });
    }

    private ToolbarButton createEditButton() {
        return uiFactory.createToolbarButton(myConstants.edit(), resources.edit(), new ToolbarButtonCallback() {
            @Override
            public void onClicked() {
                getUiHandlers().edit();
            }
        });
    }

    private ToolbarButton createDeleteButton() {
        return uiFactory.createToolbarButton(myConstants.delete(), resources.delete(), new ToolbarButtonCallback() {
            @Override
            public void onClicked() {
                getUiHandlers().delete();
            }
        });
    }
}
