/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.entity.editor;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.entity.editor.EntityEditorPresenter.MyView;
import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.util.KeyPrettifier.KeyPrettifier;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.IsWidget;
import com.gwtplatform.mvp.client.ViewImpl;

public class EntityEditorView extends ViewImpl implements MyView {
    private final FlowPanel panel;
    private final KeyPrettifier keyPrettifier;

    @Inject
    EntityEditorView(KeyPrettifier keyPrettifier) {
        this.keyPrettifier = keyPrettifier;

        panel = new FlowPanel();

        initWidget(panel);
    }

    @Override
    public void addPropertyEditor(IsWidget widget) {
        panel.add(widget);
    }

    @Override
    public void setHeader(ParsedEntity dto) {
        String text = keyPrettifier.prettifyKey(dto.getKey());
        InlineLabel headerLabel = new InlineLabel(text);
        panel.add(headerLabel);
    }
}
