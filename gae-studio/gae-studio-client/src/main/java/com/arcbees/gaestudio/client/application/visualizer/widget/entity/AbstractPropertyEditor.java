/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

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

public abstract class AbstractPropertyEditor<T> implements PropertyEditor<T> {
    @UiTemplate("AbstractPropertyEditor.ui.xml")
    static interface Binder extends UiBinder<Widget, PropertyEditorUiFields> {
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

    private final PropertyEditorUiFields fields;
    private final Widget widget;

    protected AbstractPropertyEditor(String key) {
        this.key = key;
        fields = new PropertyEditorUiFields();

        widget = UI_BINDER.createAndBindUi(fields);

        fields.key.setText(key);
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    protected void initFormWidget(IsWidget formWidget) {
        if (fields.form.getWidget() != null) {
            throw new IllegalStateException("Property Widget already set.");
        }

        fields.form.setWidget(formWidget);
    }

    @Override
    public final boolean isValid() {
        boolean valid = validate();
        if (!valid) {
            showErrors();
        }

        return valid;
    }

    protected boolean validate() {
        return true;
    }

    protected void showErrors() {
        dispatchError(key);
    }

    protected final void showErrors(Iterable<String> errors) {
        for(String error : errors) {
            showError(error);
        }
    }

    protected final void showError(String error) {
        dispatchError(key + " (" + error + ")");
    }

    private void dispatchError(String error) {
        eventBus.fireEventFromSource(new PropertyEditorErrorEvent(error), this);
    }
}