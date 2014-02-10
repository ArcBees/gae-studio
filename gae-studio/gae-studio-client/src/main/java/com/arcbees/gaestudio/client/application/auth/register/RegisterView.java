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
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.google.common.base.Strings;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import static com.google.gwt.query.client.GQuery.$;

public class RegisterView extends ViewWithUiHandlers<RegisterUiHandlers> implements RegisterPresenter.MyView {
    interface Binder extends UiBinder<Widget, RegisterView> {
    }

    @UiField
    TextBox firstName;
    @UiField
    TextBox lastName;
    @UiField
    TextBox registerEmail;
    @UiField
    PasswordTextBox registerPassword;
    @UiField
    PasswordTextBox confirmPassword;
    @UiField
    Button register;
    @UiField(provided = true)
    AjaxLoader ajaxLoader;
    @UiField
    DivElement errorMessage;

    private final AppConstants constants;

    @Inject
    RegisterView(Binder uiBinder,
                 AjaxLoader ajaxLoader,
                 AppConstants constants) {
        this.ajaxLoader = ajaxLoader;
        this.constants = constants;

        initWidget(uiBinder.createAndBindUi(this));

        $(firstName).attr("placeholder", "First Name");
        $(lastName).attr("placeholder", "Last Name");
        $(registerEmail).attr("placeholder", "Email");
        $(registerPassword).attr("placeholder", "Password");
        $(confirmPassword).attr("placeholder", "Confirm your password");
    }

    @Override
    public void resetSubmit() {
        ajaxLoader.hide();
        register.setEnabled(true);
        clearErrorMessage();
    }

    @Override
    public void resetForm() {
        firstName.setText("");
        lastName.setText("");
        registerEmail.setText("");
        registerPassword.setText("");

        clearErrorMessage();
        resetSubmit();
    }

    @UiHandler("register")
    void onRegisterClicked(ClickEvent event) {
        if (passwordMatch()) {
            ajaxLoader.show();
            register.setEnabled(false);

            getUiHandlers().register(firstName.getText(), lastName.getText(), registerEmail.getText(),
                    registerPassword.getText());
        } else {
            showErrorMessage(constants.passwordDontMatch());
        }
    }

    private Boolean passwordMatch() {
        return !Strings.isNullOrEmpty(registerPassword.getText()) &&
                !Strings.isNullOrEmpty(confirmPassword.getText()) &&
                registerPassword.getText().equals(confirmPassword.getText());
    }

    private void showErrorMessage(String message) {
        errorMessage.setInnerText(message);
        setErrorMessageOpacity(1.0f);
    }

    private void clearErrorMessage() {
        errorMessage.setInnerText("");
        setErrorMessageOpacity(0f);
    }

    private void setErrorMessageOpacity(float opacity) {
        $(errorMessage).css("opacity", Float.toString(opacity));
    }
}
