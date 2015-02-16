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

package com.arcbees.gaestudio.client.application.profiler.widget;

import java.util.List;

import com.arcbees.gaestudio.client.application.ui.ToolbarButton;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.client.ui.PanelToggle;
import com.arcbees.gaestudio.client.ui.PanelToggleFactory;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import static com.google.gwt.query.client.GQuery.$;

public class ToolbarView extends ViewWithUiHandlers<ToolbarUiHandlers>
        implements ToolbarPresenter.MyView, PanelToggle.ToggleHandler {
    interface Binder extends UiBinder<Widget, ToolbarView> {
    }

    private static final int PANEL_WIDTH_CLOSED = 50;
    private static final int PANEL_WIDTH_OPENED = 130;

    @UiField(provided = true)
    AppResources resources;
    @UiField
    HTMLPanel buttons;

    private final PanelToggle toggle;

    @Inject
    ToolbarView(
            Binder uiBinder,
            AppResources resources,
            PanelToggleFactory panelToggleFactory) {
        this.resources = resources;

        initWidget(uiBinder.createAndBindUi(this));

        toggle = panelToggleFactory.create(this);
        $(toggle).addClass(resources.styles().profilerToggle());
    }

    @Override
    public void onToggle() {
        final boolean isOpened = toggle.isOpen();
        if (isOpened) {
            $(buttons).width(PANEL_WIDTH_CLOSED);
        } else {
            $(buttons).width(PANEL_WIDTH_OPENED);
        }

        $(buttons).delay(275, new Function() {
            @Override
            public void f() {
                getUiHandlers().onToggle(isOpened);
            }
        });
    }

    @Override
    public void setButtons(List<ToolbarButton> buttonsToSet) {
        buttons.clear();

        this.buttons.add(toggle);

        for (ToolbarButton button : buttonsToSet) {
            buttons.add(button);
        }
    }

    @Override
    public void setVisible(boolean visible) {
        asWidget().setVisible(visible);
    }
}
