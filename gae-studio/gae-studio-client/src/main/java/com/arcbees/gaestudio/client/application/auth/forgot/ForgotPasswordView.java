/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.auth.forgot;

import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class ForgotPasswordView extends ViewWithUiHandlers<ForgotPasswordUiHandlers>
        implements ForgotPasswordPresenter.MyView {
    interface Binder extends UiBinder<Widget, ForgotPasswordView> {
    }

    @UiField
    TextBox email;
    @UiField
    Button submit;

    @Inject
    ForgotPasswordView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("submit")
    void onRegisterClicked(ClickEvent event) {
        getUiHandlers().forgotPassword(email.getText());
    }
}
