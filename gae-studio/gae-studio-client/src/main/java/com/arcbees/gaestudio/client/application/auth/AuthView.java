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
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
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
    @UiField
    DivElement errorMessage;

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
                getUiHandlers().redirectToRegister();
            }
        });

        $(loginFormHelper.getForgotLinkElement()).click(new Function() {
            @Override
            public void f() {
                getUiHandlers().redirectToForgotPassword();
            }
        });

        loginForm.setWidget(loginFormHelper.getLoginFormPanel());
    }

    @Override
    public void showErrorMessage(String message) {
        this.errorMessage.setInnerText(message);

        setErrorMessageOpacity(1.0f);
        showRedBoxes();

        resetLoginForm();
    }

    @Override
    public void resetLoginForm() {
        hideAjaxLoader();
        setLoginButtonEnabled(true);
    }

    private void setLoginButtonEnabled(boolean enabled) {
        loginFormHelper.getSubmitButton().setDisabled(!enabled);
    }

    private void doLogin() {
        setErrorMessageOpacity(0.0f);
        showAjaxLoader();
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

    private void setErrorMessageOpacity(float opacity) {
        $(errorMessage).css("opacity", Float.toString(opacity));
    }

    private void showAjaxLoader() {
        Image ajaxLoader = buildAjaxLoader();

        $(loginFormHelper.getSubmitButton()).before(ajaxLoader.asWidget().getElement());
    }

    private void hideAjaxLoader() {
        $(loginFormHelper.getSubmitButton()).prev().remove();
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
