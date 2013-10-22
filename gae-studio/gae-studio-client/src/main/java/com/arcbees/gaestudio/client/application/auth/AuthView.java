/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.auth;

import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import static com.google.gwt.query.client.GQuery.$;

public class AuthView extends ViewWithUiHandlers<AuthUiHandlers> implements AuthPresenter.MyView {
    interface Binder extends UiBinder<Widget, AuthView> {
    }

    @UiField
    SimplePanel loginForm;
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
    @UiField
    HTMLPanel registerForm;

    private final LoginHelper loginHelper;
    private final FormPanel form;

    @Inject
    AuthView(Binder uiBinder,
             LoginHelper loginHelper) {
        initWidget(uiBinder.createAndBindUi(this));

        this.loginHelper = loginHelper;
        injectLoginFunction();

        $(loginHelper.getRegisterLinkElement()).click(new Function() {
            @Override
            public void f() {
                onRegisterLinkClicked();
            }
        });

        form = loginHelper.getLoginFormPanel();
        loginForm.setWidget(form);
    }

    @Override
    public void reload() {
        Window.Location.reload();
    }

    @UiHandler("register")
    void onRegisterClicked(ClickEvent event) {
        getUiHandlers().register(firstName.getText(), lastName.getText(), registerEmail.getText(),
                registerPassword.getText());
    }

    @UiHandler("loginLink")
    void onLoginLinkClicked(ClickEvent event) {
        loginForm.setVisible(true);
        registerForm.setVisible(false);
    }

    private void onRegisterLinkClicked() {
        loginForm.setVisible(false);
        registerForm.setVisible(true);
    }

    private void doLogin() {
        getUiHandlers().login(loginHelper.getUsername(), loginHelper.getPassword());
    }

    private native void injectLoginFunction() /*-{
        var instance = this;
        $wnd.__gwt_login = function () {
            instance.@com.arcbees.gaestudio.client.application.auth.AuthView::doLogin()();
        }
    }-*/;
}
