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

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import static com.google.gwt.query.client.GQuery.$;

public class AuthView extends ViewWithUiHandlers<AuthUiHandlers> implements AuthPresenter.MyView {
    interface Binder extends UiBinder<Widget, AuthView> {
    }

    @UiField
    SimplePanel loginForm;

    private final LoginFormHelper loginFormHelper;

    @Inject
    AuthView(Binder uiBinder,
             LoginFormHelper loginFormHelper) {
        initWidget(uiBinder.createAndBindUi(this));

        this.loginFormHelper = loginFormHelper;
        injectLoginFunction();

        $(loginFormHelper.getRegisterLinkElement()).click(new Function() {
            @Override
            public void f() {
                onRegisterLinkClicked();
            }
        });

        $(loginFormHelper.getForgotLinkElement()).click(new Function() {
            @Override
            public void f() {
                onForgotPasswordLinkClicked();
            }
        });

        $(loginFormHelper.getUsernameElement()).keydown(new Function() {
            @Override
            public boolean f(Event event) {
                if (event.getKeyCode() == KeyCodes.KEY_ENTER) {
                    doLogin();
                }

                return true;
            }
        });

        $(loginFormHelper.getPasswordElement()).keydown(new Function() {
            @Override
            public boolean f(Event event) {
                if (event.getKeyCode() == KeyCodes.KEY_ENTER) {
                    doLogin();
                }

                return true;
            }
        });

        loginForm.setWidget(loginFormHelper.getLoginFormPanel());
    }

    private void onForgotPasswordLinkClicked() {
        getUiHandlers().redirectToForgotPassword();
    }

    private void onRegisterLinkClicked() {
        getUiHandlers().redirectToRegister();
    }

    private void doLogin() {
        getUiHandlers().login(loginFormHelper.getUsername(), loginFormHelper.getPassword());
    }

    private native void injectLoginFunction() /*-{
        var instance = this;
        $wnd.__gwt_login = function () {
            instance.@com.arcbees.gaestudio.client.application.auth.AuthView::doLogin()();
        }
    }-*/;
}
