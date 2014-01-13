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

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
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
    private final AppResources appResources;

    @Inject
    AuthView(Binder uiBinder,
             LoginFormHelper loginFormHelper,
             AppResources appResources) {
        this.appResources = appResources;
        this.loginFormHelper = loginFormHelper;

        initWidget(uiBinder.createAndBindUi(this));
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

        loginForm.setWidget(loginFormHelper.getLoginFormPanel());
    }

    private void onForgotPasswordLinkClicked() {
        getUiHandlers().redirectToForgotPassword();
    }

    private void onRegisterLinkClicked() {
        getUiHandlers().redirectToRegister();
    }

    private void doLogin() {
        showAjaxLoader();
        getUiHandlers().login(loginFormHelper.getUsername(), loginFormHelper.getPassword());
    }

    private void showAjaxLoader() {
        Image ajaxLoader = buildAjaxLoader();
        loginFormHelper.getSubmitButton().setDisabled(true);
        $(loginFormHelper.getSubmitButton()).before(ajaxLoader.asWidget().getElement());
    }

    private Image buildAjaxLoader() {
        Image ajaxLoader = new Image();
        ajaxLoader.setResource(appResources.ajaxLoader30px());
        ajaxLoader.addStyleName(appResources.styles().loginAjaxLoader());

        return ajaxLoader;
    }

    private native void injectLoginFunction() /*-{
        var instance = this;
        $wnd.__gwt_login = function () {
            instance.@com.arcbees.gaestudio.client.application.auth.AuthView::doLogin()();
        }
    }-*/;
}
