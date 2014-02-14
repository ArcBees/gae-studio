/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.auth.register;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.arcbees.gaestudio.client.application.ui.AjaxLoader;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.shared.auth.User;
import com.arcbees.gquery.tooltip.client.Tooltip;
import com.arcbees.gquery.tooltip.client.TooltipOptions;
import com.google.common.base.Strings;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

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
    private final AppResources resources;

    @Inject
    RegisterView(Binder uiBinder,
                 AjaxLoader ajaxLoader,
                 Driver driver,
                 AppResources resources,
                 AppConstants constants) {
        this.ajaxLoader = ajaxLoader;
        this.driver = driver;
        this.resources = resources;
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
    public void edit(User user) {
        confirmPassword.setText("");
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

        for (ConstraintViolation<User> violation : violations) {
            String nameSelector = "[name = \"" + violation.getPropertyPath().toString() + "\"]";
            $(nameSelector).addClass(resources.styles().errorField());
            $(nameSelector).as(Tooltip.Tooltip).tooltip(new TooltipOptions()
                    .withContent(violation.getMessage())
                    .withPlacement(TooltipOptions.TooltipPlacement.TOP));
        }

        return violations.isEmpty();
    }

    private Boolean passwordMatch() {
        String passwordText = password.getText();
        String confirmPasswordText = confirmPassword.getText();

        return !Strings.isNullOrEmpty(passwordText) &&
                !Strings.isNullOrEmpty(confirmPasswordText) &&
                passwordText.equals(confirmPasswordText);
    }

    private void showErrorMessage(String message) {
        errorMessage.setInnerText(message);
        errorMessage.getStyle().setDisplay(Style.Display.BLOCK);
    }

    private void clearErrorMessage() {
        errorMessage.getStyle().setDisplay(Style.Display.NONE);
        errorMessage.setInnerText("");
        $("." + resources.styles().errorField()).each(new Function() {
            @Override
            public void f(Element e) {
                $(e).removeClass(resources.styles().errorField());
                $(e).as(Tooltip.Tooltip).destroy();
            }
        });
    }
}
