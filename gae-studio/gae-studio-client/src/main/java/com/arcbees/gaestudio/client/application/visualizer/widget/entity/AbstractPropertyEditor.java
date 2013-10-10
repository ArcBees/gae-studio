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

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractPropertyEditor<T> implements PropertyEditor<T> {
    static interface Binder extends UiBinder<Widget, AbstractPropertyEditor> {
    }

    private static final Binder UI_BINDER = GWT.create(Binder.class);

    @UiField
    Label key;
    @UiField
    SimplePanel form;

    private final Widget widget;

    protected AbstractPropertyEditor(String key) {
        widget = UI_BINDER.createAndBindUi(this);

        this.key.setText(key);
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    protected void initFormWidget(IsWidget formWidget) {
        if (form.getWidget() != null) {
            throw new IllegalStateException("Property Widget already set.");
        }

        form.setWidget(formWidget);
    }
}
