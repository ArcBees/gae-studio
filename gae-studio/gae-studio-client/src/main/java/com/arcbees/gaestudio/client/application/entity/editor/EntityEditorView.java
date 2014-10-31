/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.entity.editor;

import com.arcbees.gaestudio.client.application.entity.editor.EntityEditorPresenter.MyView;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
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

    @Override
    public void setHeader(String text) {
        InlineLabel headerLabel = new InlineLabel(text);
        panel.add(headerLabel);
    }
}
