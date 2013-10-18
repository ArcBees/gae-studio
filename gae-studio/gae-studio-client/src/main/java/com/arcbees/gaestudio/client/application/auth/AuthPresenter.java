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

import com.arcbees.gaestudio.client.application.ApplicationPresenter;
import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.rest.AuthService;
import com.arcbees.gaestudio.client.util.CurrentUser;
import com.arcbees.gaestudio.shared.auth.Token;
import com.arcbees.gaestudio.shared.auth.User;
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
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.auth)
    @NoGatekeeper
    interface MyProxy extends ProxyPlace<AuthPresenter> {
    }

    private final AppConstants appConstants;
    private final PlaceManager placeManager;
    private final CurrentUser currentUser;
    private final AuthService authService;

    @Inject
    AuthPresenter(EventBus eventBus,
                  MyView view,
                  MyProxy proxy,
                  AppConstants appConstants,
                  PlaceManager placeManager,
                  CurrentUser currentUser,
                  AuthService authService) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.appConstants = appConstants;
        this.placeManager = placeManager;
        this.currentUser = currentUser;
        this.authService = authService;

        getView().setUiHandlers(this);
    }

    @Override
    public void register(String firstName,
                         String lastName,
                         final String email,
                         final String password) {
        authService.register(email, password, firstName, lastName, new MethodCallback<User>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                DisplayMessageEvent.fire(AuthPresenter.this,
                        new Message(appConstants.unableToRegister(), MessageStyle.ERROR));
            }

            @Override
            public void onSuccess(Method method, User user) {
                login(email, password);
            }
        });
    }

    @Override
    public void login(String email, String password) {
        authService.login(email, password, new MethodCallback<Token>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                DisplayMessageEvent.fire(AuthPresenter.this,
                        new Message(appConstants.unableToLogin(), MessageStyle.ERROR));
            }

            @Override
            public void onSuccess(Method method, Token token) {
                onLoginSuccess();
            }
        });
    }

    private void onLoginSuccess() {
        DisplayMessageEvent.fire(this, new Message(appConstants.loggedInSuccessfully(), MessageStyle.ERROR));

        currentUser.setLoggedIn(true);

        PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.visualizer).build();
        placeManager.revealPlace(placeRequest);
    }
}
