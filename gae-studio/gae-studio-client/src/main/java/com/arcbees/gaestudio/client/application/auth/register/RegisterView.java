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
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.arcbees.gaestudio.client.application.ui.AjaxLoader;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.shared.auth.User;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import java.util.List;
import java.util.Set;

import static com.google.gwt.query.client.GQuery.$;

public class RegisterView extends ViewWithUiHandlers<RegisterUiHandlers>
        implements RegisterPresenter.MyView, Editor<User> {
    interface Binder extends UiBinder<Widget, RegisterView> {
    }

    interface Driver extends SimpleBeanEditorDriver<User, RegisterView> {
    }

    @UiField
    @Path("profile.firstName")
    TextBox firstName;
    @UiField
    @Path("profile.lastName")
    TextBox lastName;
    @UiField
    TextBox email;
    @UiField
    PasswordTextBox password;
    @UiField
    @Ignore
    PasswordTextBox confirmPassword;
    @UiField
    Button register;
    @UiField(provided = true)
    AjaxLoader ajaxLoader;
    @UiField
    DivElement errorMessage;

    private final Driver driver;
    private final AppConstants constants;

    @Inject
    RegisterView(Binder uiBinder,
                 AjaxLoader ajaxLoader,
                 Driver driver,
                 AppConstants constants) {
        this.ajaxLoader = ajaxLoader;
        this.driver = driver;
        this.constants = constants;

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        $(firstName).attr("placeholder", "First Name");
        $(lastName).attr("placeholder", "Last Name");
        $(email).attr("placeholder", "Email");
        $(password).attr("placeholder", "Password");
        $(confirmPassword).attr("placeholder", "Confirm your password");
    }

    @Override
    public void setupForm(User user) {
        ajaxLoader.hide();
        register.setEnabled(true);
        driver.edit(user);

        clearErrorMessage();
    }

    @UiHandler("register")
    void onRegisterClicked(ClickEvent event) {
        User user = driver.flush();
        if (validateEntity(user)) {
            if (passwordMatch()) {
                getUiHandlers().register(user);
            } else {
                showErrorMessage(constants.passwordDontMatch());
            }
        }
    }

    private Boolean validateEntity(User user) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        return violations.isEmpty();
    }

    private Boolean passwordMatch() {
        return !Strings.isNullOrEmpty(password.getText()) &&
                !Strings.isNullOrEmpty(confirmPassword.getText()) &&
                password.getText().equals(confirmPassword.getText());
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
