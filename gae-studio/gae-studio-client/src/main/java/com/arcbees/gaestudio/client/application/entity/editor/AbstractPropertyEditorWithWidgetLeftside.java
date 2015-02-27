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

package com.arcbees.gaestudio.client.application.entity.editor;

import javax.inject.Inject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

public abstract class AbstractPropertyEditorWithWidgetLeftside<T> implements PropertyEditor<T> {
    @UiTemplate("AbstractPropertyEditorWithWidgetLeftside.ui.xml")
    interface Binder extends UiBinder<Widget, PropertyEditorUiFields> {
    }

    /**
     * Wrapper class to make easier for subclasses to use UiBinder. Otherwise UiBinder will require subclasses'
     * UITemplate to have elements for the fields defined here.
     */
    static class PropertyEditorUiFields {
        @UiField
        Label key;
        @UiField
        SimplePanel form;
    }

    private static final Binder UI_BINDER = GWT.create(Binder.class);

    @Inject
    private static EventBus eventBus;

    protected final String key;
    protected final PropertyEditorUiFields fields;
    protected final Widget widget;

    protected AbstractPropertyEditorWithWidgetLeftside(String key) {
        this.key = key;
        fields = new PropertyEditorUiFields();

        widget = UI_BINDER.createAndBindUi(fields);

        fields.key.setText(key);
        fields.form.ensureDebugId(key);
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    @Override
    public final boolean isValid() {
        boolean valid = validate();
        if (!valid) {
            showErrors();
        }

        return valid;
    }

    protected void initFormWidget(IsWidget formWidget) {
        if (fields.form.getWidget() != null) {
            throw new IllegalStateException("Property Widget already set.");
        }

        fields.form.setWidget(formWidget);
    }

    protected boolean validate() {
        return true;
    }

    protected void showErrors() {
        dispatchError(key);
    }

    protected final void showErrors(Iterable<String> errors) {
        for (String error : errors) {
            showError(error);
        }
    }

    protected final void showError(String error) {
        dispatchError(key + " (" + error + ")");
    }

    protected void setKeyStyle(String keyStyle) {
        fields.key.setStyleName(keyStyle);
    }

    protected void dispatchError(String error) {
        eventBus.fireEventFromSource(new PropertyEditorErrorEvent(error), this);
    }
}
