/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.entity;

import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class EditEntityView extends ViewWithUiHandlers<EditEntityUiHandlers> implements EditEntityPresenter.MyView {
    interface Binder extends UiBinder<Widget, EditEntityView> {
    }

    @UiField
    Button save;
    @UiField
    Button cancel;
    @UiField
    SimplePanel editorPanel;
    @UiField
    FlowPanel errors;

    @Inject
    EditEntityView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void showError(String message) {
        errors.setVisible(true);
        errors.add(new Label(message));
    }

    @Override
    public void clearErrors() {
        errors.setVisible(false);
        errors.clear();
    }

    @Override
    public void showErrorsTitle(String title) {
        errors.insert(new Label(title), 0);
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        super.setInSlot(slot, content);

        errors.setVisible(false);

        if (slot == EditEntityPresenter.EDITOR_SLOT) {
            editorPanel.setWidget(content);
        }
    }

    @UiHandler("save")
    void onSaveClicked(ClickEvent event) {
        getUiHandlers().save();
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        getUiHandlers().cancel();
    }
}
