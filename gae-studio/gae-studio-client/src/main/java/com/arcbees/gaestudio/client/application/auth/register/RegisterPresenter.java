/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.auth.register;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.arcbees.gaestudio.client.application.ApplicationPresenter;
import com.arcbees.gaestudio.client.application.auth.LoginHelper;
import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.rest.AuthService;
import com.arcbees.gaestudio.shared.auth.User;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

public class RegisterPresenter extends Presenter<RegisterPresenter.MyView, RegisterPresenter.MyProxy> implements
        RegisterUiHandlers {
    interface MyView extends View, HasUiHandlers<RegisterUiHandlers> {
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.register)
    @NoGatekeeper
    interface MyProxy extends ProxyPlace<RegisterPresenter> {
    }

    private final AppConstants appConstants;
    private final LoginHelper loginHelper;
    private final AuthService authService;

    @Inject
    RegisterPresenter(EventBus eventBus,
                      MyView view,
                      MyProxy proxy,
                      AppConstants appConstants,
                      AuthService authService,
                      LoginHelper loginHelper) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.appConstants = appConstants;
        this.loginHelper = loginHelper;
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
                DisplayMessageEvent.fire(RegisterPresenter.this,
                        new Message(appConstants.unableToRegister(), MessageStyle.ERROR));
            }

            @Override
            public void onSuccess(Method method, User user) {
                loginHelper.login(email, password);
            }
        });
    }
}
