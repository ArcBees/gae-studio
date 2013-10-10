/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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
