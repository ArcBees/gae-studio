/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.license;

import javax.inject.Inject;

import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class LicenseView extends ViewWithUiHandlers<LicenseUiHandlers> implements LicensePresenter.MyView {
    interface Binder extends UiBinder<Widget, LicenseView> {
    }

    @UiField
    HeadingElement message;
    @UiField
    HTMLPanel keyEntrySection;
    @UiField
    TextBox key;

    @Inject
    LicenseView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void showMessage(String message) {
        this.message.setInnerSafeHtml(SafeHtmlUtils.fromString(message));
    }

    @Override
    public void setKeyEntrySectionVisible(boolean visible) {
        keyEntrySection.setVisible(visible);
    }

    @Override
    public String getKey() {
        return key.getText();
    }

    @UiHandler("register")
    void handleClick(ClickEvent event) {
        getUiHandlers().onRegister();
    }
}
