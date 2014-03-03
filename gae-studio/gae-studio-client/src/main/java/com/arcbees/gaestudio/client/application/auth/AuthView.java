/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.auth;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.ui.AjaxLoader;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
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
    @UiField
    DivElement errorMessage;

    private final LoginFormHelper loginFormHelper;
    private final AjaxLoader ajaxLoader;
    private final Function loginOnEnter = new Function() {
        @Override
        public boolean f(Event event) {
            if (event.getKeyCode() == KeyCodes.KEY_ENTER) {
                doLogin();
            }

            return true;
        }
    };

    @Inject
    AuthView(Binder uiBinder,
             AppResources appResources,
             LoginFormHelper loginFormHelper,
             AjaxLoader ajaxLoader) {
        this.ajaxLoader = ajaxLoader;
        this.loginFormHelper = loginFormHelper;

        initWidget(uiBinder.createAndBindUi(this));
        injectLoginFunction();

        $(loginFormHelper.getRegisterLinkElement()).click(new Function() {
            @Override
            public void f() {
                getUiHandlers().redirectToRegister();
            }
        });

        $(loginFormHelper.getForgotLinkElement()).click(new Function() {
            @Override
            public void f() {
                getUiHandlers().redirectToForgotPassword();
            }
        });

        $(loginFormHelper.getUsernameElement()).keydown(loginOnEnter);

        $(loginFormHelper.getPasswordElement()).keydown(loginOnEnter);

        loginForm.setWidget(loginFormHelper.getLoginFormPanel());

        loginFormHelper.getLoginFormPanel().add(ajaxLoader);
        ajaxLoader.asWidget().addStyleName(appResources.styles().loginAjaxLoader());

        setErrorMessageVisible(false);
    }

    @Override
    public void showErrorMessage(String message) {
        this.errorMessage.setInnerText(message);

        setErrorMessageVisible(true);
        showRedBoxes();

        resetLoginForm();
    }

    @Override
    public void resetLoginForm() {
        ajaxLoader.hide();
        setLoginButtonEnabled(true);
    }

    private void setLoginButtonEnabled(boolean enabled) {
        loginFormHelper.getSubmitButton().setDisabled(!enabled);
    }

    private void doLogin() {
        setErrorMessageVisible(false);
        ajaxLoader.show();
        setLoginButtonEnabled(false);
        hideRedBoxes();

        getUiHandlers().login(loginFormHelper.getUsername(), loginFormHelper.getPassword());
    }

    private void hideRedBoxes() {
        formFields().css("outline", "none");
    }

    private void showRedBoxes() {
        formFields().css("outline", "#ff5400 solid 2px");
        formFields().css("outline-offset", "-2px");
    }

    private GQuery formFields() {
        return $("input", loginForm);
    }

    private void setErrorMessageVisible(boolean visible) {
        errorMessage.getStyle().setVisibility(visible ? Style.Visibility.VISIBLE : Style.Visibility.HIDDEN);
    }

    private native void injectLoginFunction() /*-{
        var instance = this;
        $wnd.__gwt_login = function () {
            instance.@com.arcbees.gaestudio.client.application.auth.AuthView::doLogin()();
        }
    }-*/;
}
