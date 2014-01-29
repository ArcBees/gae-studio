/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.widget;

import java.util.List;

import com.arcbees.gaestudio.client.application.ui.ToolbarButton;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.client.ui.PanelToggle;
import com.arcbees.gaestudio.client.ui.PanelToggleFactory;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

import static com.google.gwt.query.client.GQuery.$;

public class ToolbarView extends ViewImpl implements ToolbarPresenter.MyView, PanelToggle.ToggleHandler {
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
    ToolbarView(Binder uiBinder,
                AppResources resources,
                PanelToggleFactory panelToggleFactory) {
        this.resources = resources;

        initWidget(uiBinder.createAndBindUi(this));

        toggle = panelToggleFactory.create(this);
        toggle.asWidget().addStyleName(resources.styles().profilerToggle());
    }

    @Override
    public void onToggle() {
        if (toggle.getDirection().equals(PanelToggle.Direction.LEFT)) {
            $(buttons).width(PANEL_WIDTH_CLOSED);
        } else {
            $(buttons).width(PANEL_WIDTH_OPENED);
        }
    }

    @Override
    public void setButtons(List<ToolbarButton> buttonsToSet) {
        buttons.clear();

        for(ToolbarButton button : buttonsToSet) {
            this.buttons.add(button);
        }

        this.buttons.add(toggle);
    }

    @Override
    public void setVisible(boolean visible) {
        asWidget().setVisible(visible);
    }
}
