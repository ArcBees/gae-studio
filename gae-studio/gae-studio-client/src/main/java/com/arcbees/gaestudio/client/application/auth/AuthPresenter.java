/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.auth;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.rest.AuthService;
import com.arcbees.gaestudio.shared.auth.Token;
import com.google.gwt.http.client.Response;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

public class AuthPresenter extends Presenter<AuthPresenter.MyView, AuthPresenter.MyProxy> implements AuthUiHandlers {
    interface MyView extends View, HasUiHandlers<AuthUiHandlers> {
        void showErrorMessage(String errorMessage);
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.auth)
    @NoGatekeeper
    interface MyProxy extends ProxyPlace<AuthPresenter> {
    }

    private final PlaceManager placeManager;
    private final LoginHelper loginHelper;
    private final AuthService authService;
    private final AppConstants appConstants;

    @Inject
    AuthPresenter(EventBus eventBus,
                  MyView view,
                  MyProxy proxy,
                  PlaceManager placeManager,
                  LoginHelper loginHelper,
                  AuthService authService,
                  AppConstants appConstants) {
        super(eventBus, view, proxy, RevealType.Root);

        this.placeManager = placeManager;
        this.loginHelper = loginHelper;
        this.authService = authService;
        this.appConstants = appConstants;

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
        authService.login(email, password, new MethodCallback<Token>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                displayError(method);
            }

            @Override
            public void onSuccess(Method method, Token token) {
                loginHelper.reloadApp();
            }
        });
    }

    private void displayError(Method method) {
        Response response = method.getResponse();
        int statusCode = response.getStatusCode();

        if (statusCode == Response.SC_UNAUTHORIZED) {
            getView().showErrorMessage(appConstants.wrongPwdOrEmail());
        } else {
            getView().showErrorMessage(appConstants.oops());
        }
    }
}
