/**
 * Copyright 2011 ArcBees Inc.
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

package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

import com.arcbees.gaestudio.client.application.visualizer.widget.entity.EntityEditorPresenter.MyView;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.gwtplatform.mvp.client.ViewImpl;

public class EntityEditorView extends ViewImpl implements MyView {
    private final FlowPanel panel;

    EntityEditorView() {
        panel = new FlowPanel();

        initWidget(panel);
    }

    @Override
    public void addPropertyEditor(IsWidget widget) {
        panel.add(widget);
    }
}
