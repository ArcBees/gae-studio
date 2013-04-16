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

package com.arcbees.gaestudio.client.application.visualizer;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class VisualizerView extends ViewImpl implements VisualizerPresenter.MyView {
    public interface Binder extends UiBinder<Widget, VisualizerView> {
    }

    @UiField
    SimplePanel entityListPanel;
    @UiField
    SimplePanel toolbar;

    @Inject
    public VisualizerView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (content != null) {
            if (slot == VisualizerPresenter.TYPE_SetEntityListPanelContent) {
                entityListPanel.setWidget(content);
            } else if (slot == VisualizerPresenter.TYPE_SetToolbarContent) {
                toolbar.setWidget(content);
            }
        }
    }
}
