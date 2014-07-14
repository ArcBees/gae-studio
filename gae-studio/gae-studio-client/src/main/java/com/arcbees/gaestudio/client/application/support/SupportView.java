/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.support;

import javax.inject.Inject;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

public class SupportView extends PopupViewWithUiHandlers<SupportUiHandlers>
        implements SupportPresenter.MyView, Editor<SupportMessage> {
    interface Binder extends UiBinder<Widget, SupportView> {
    }

    interface Driver extends SimpleBeanEditorDriver<SupportMessage, SupportView> {
    }

    @UiField
    TextBox name;
    @UiField
    TextBox email;
    @UiField
    TextBox subject;
    @UiField
    TextArea body;

    private final Driver driver;

    @Inject
    SupportView(EventBus eventBus,
                Binder binder,
                Driver driver) {
        super(eventBus);
        this.driver = driver;

        initWidget(binder.createAndBindUi(this));

        driver.initialize(this);
    }

    @Override
    public void edit(SupportMessage supportMessage) {
        driver.edit(supportMessage);
    }

    @UiHandler("cancel")
    void onCancel(ClickEvent event) {
        hide();
    }

    @UiHandler("send")
    void onSend(ClickEvent event) {
        getUiHandlers().send(driver.flush());
    }
}
