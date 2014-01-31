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

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.ui.ToolbarButton;
import com.arcbees.gaestudio.client.application.visualizer.event.ToolbarToggleEvent2;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class ToolbarPresenter extends PresenterWidget<ToolbarPresenter.MyView> implements ToolbarUiHandlers {
    interface MyView extends View, HasUiHandlers<ToolbarUiHandlers> {
        void setButtons(List<ToolbarButton> buttons);

        void setVisible(boolean visible);
    }

    @Inject
    ToolbarPresenter(EventBus eventBus,
                     MyView view) {
        super(eventBus, view);

        getView().setUiHandlers(this);
    }

    @Override
    public void onToggle(boolean opened) {
        ToolbarToggleEvent2.fire(this, opened);
    }

    public void setButtons(List<ToolbarButton> buttons) {
        getView().setButtons(buttons);
    }

    public void setVisible(boolean visible) {
        getView().setVisible(visible);
    }
}
