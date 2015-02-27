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

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.ui.ToolbarButton;
import com.arcbees.gaestudio.client.application.visualizer.event.ToolbarToggleEvent;
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
    ToolbarPresenter(
            EventBus eventBus,
            MyView view) {
        super(eventBus, view);

        getView().setUiHandlers(this);
    }

    @Override
    public void onToggle(boolean opened) {
        ToolbarToggleEvent.fire(this, opened);
    }

    public void setButtons(List<ToolbarButton> buttons) {
        getView().setButtons(buttons);
    }

    public void setVisible(boolean visible) {
        getView().setVisible(visible);
    }
}
