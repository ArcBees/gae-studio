/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.auth;

import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.rest.AuthService;
import com.arcbees.gaestudio.client.util.RestCallbackImpl;
import com.arcbees.gaestudio.shared.auth.Token;
import com.google.gwt.http.client.Response;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.shared.RestDispatch;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

public class AuthPresenter extends Presenter<AuthPresenter.MyView, AuthPresenter.MyProxy> implements AuthUiHandlers {
    interface MyView extends View, HasUiHandlers<AuthUiHandlers> {
        void showErrorMessage(String errorMessage);

        void resetLoginForm();
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.auth)
    @NoGatekeeper
    interface MyProxy extends ProxyPlace<AuthPresenter> {
    }

    private final PlaceManager placeManager;
    private final RestDispatch restDispatch;
    private final AuthService authService;
    private final AppConstants appConstants;
    private final LoginHelper loginHelper;

    @Inject
    AuthPresenter(EventBus eventBus,
                  MyView view,
                  MyProxy proxy,
                  PlaceManager placeManager,
                  RestDispatch restDispatch,
                  AuthService authService,
                  AppConstants appConstants,
                  LoginHelper loginHelper) {
        super(eventBus, view, proxy, RevealType.Root);

        this.placeManager = placeManager;
        this.restDispatch = restDispatch;
        this.authService = authService;
        this.appConstants = appConstants;
        this.loginHelper = loginHelper;

        getView().setUiHandlers(this);
    }

    @Override
    public void redirectToRegister() {
        PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.register).build();
        placeManager.revealPlace(placeRequest);
    }

    @Override
    public void redirectToForgotPassword() {
        PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.forgotpass).build();
        placeManager.revealPlace(placeRequest);
    }

    @Override
    public void login(String email, String password) {
        restDispatch.execute(authService.login(email, password),
                new RestCallbackImpl<Token>() {
                    @Override
                    public void onSuccess(Token result) {
                        loginHelper.login();
                    }

                    @Override
                    public void setResponse(Response response) {
                        displayError(response);
                    }
                });
    }

    @Override
    protected void onHide() {
        super.onHide();

        getView().resetLoginForm();
    }

    private void displayError(Response response) {
        int statusCode = response.getStatusCode();

        if (statusCode == Response.SC_UNAUTHORIZED) {
            getView().showErrorMessage(appConstants.wrongPwdOrEmail());
        } else if (statusCode != Response.SC_OK) {
            getView().showErrorMessage(appConstants.oops());
        }
    }
}
