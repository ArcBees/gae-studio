/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.auth.register;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.ui.AjaxLoader;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import static com.google.gwt.query.client.GQuery.$;

public class RegisterView extends ViewWithUiHandlers<RegisterUiHandlers> implements RegisterPresenter.MyView {
    interface Binder extends UiBinder<Widget, RegisterView> {
    }

    private final static Integer REGISTER_PANEL = 0;
    private final static Integer ACTIVATE_PANEL = 1;

    @UiField
    DeckPanel registerPanel;
    @UiField
    TextBox firstName;
    @UiField
    TextBox lastName;
    @UiField
    TextBox registerEmail;
    @UiField
    PasswordTextBox registerPassword;
    @UiField
    Button register;
    @UiField(provided = true)
    AjaxLoader ajaxLoader;

    @Inject
    RegisterView(Binder uiBinder,
                 AjaxLoader ajaxLoader) {
        this.ajaxLoader = ajaxLoader;

        initWidget(uiBinder.createAndBindUi(this));

        $(firstName).attr("placeholder", "First Name");
        $(lastName).attr("placeholder", "Last Name");
        $(registerEmail).attr("placeholder", "Email");
        $(registerPassword).attr("placeholder", "Password");
    }

    @Override
    public void resetSubmit() {
        ajaxLoader.hide();
        register.setEnabled(true);
    }

    @Override
    public void resetForm() {
        firstName.setText("");
        lastName.setText("");
        registerEmail.setText("");
        registerPassword.setText("");
        registerPanel.showWidget(REGISTER_PANEL);

        resetSubmit();
    }

    @Override
    public void showConfirmActivation() {
        registerPanel.showWidget(ACTIVATE_PANEL);
    }

    @UiHandler("register")
    void onRegisterClicked(ClickEvent event) {
        ajaxLoader.show();
        register.setEnabled(false);

        getUiHandlers().register(firstName.getText(), lastName.getText(), registerEmail.getText(),
                registerPassword.getText());
    }
}
