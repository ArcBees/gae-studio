/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.auth.forgot;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.arcbees.gaestudio.client.application.ApplicationPresenter;
import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.rest.AuthService;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

public class ForgotPasswordPresenter extends Presenter<ForgotPasswordPresenter.MyView, ForgotPasswordPresenter.MyProxy>
        implements ForgotPasswordUiHandlers {
    interface MyView extends View, HasUiHandlers<ForgotPasswordUiHandlers> {
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.forgotpass)
    @NoGatekeeper
    interface MyProxy extends ProxyPlace<ForgotPasswordPresenter> {
    }

    private final AppConstants appConstants;
    private final AuthService authService;

    @Inject
    ForgotPasswordPresenter(EventBus eventBus,
                            MyView view,
                            MyProxy proxy,
                            AppConstants appConstants,
                            AuthService authService) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.appConstants = appConstants;
        this.authService = authService;

        getView().setUiHandlers(this);
    }

    @Override
    public void forgotPassword(String email) {
        authService.generateResetToken(email, new MethodCallback<Void>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                DisplayMessageEvent.fire(ForgotPasswordPresenter.this,
                        new Message(appConstants.unableToRegister(), MessageStyle.ERROR));
            }

            @Override
            public void onSuccess(Method method, Void result) {
                DisplayMessageEvent.fire(ForgotPasswordPresenter.this,
                        new Message(appConstants.passwordReset(), MessageStyle.ERROR));
            }
        });
    }
}
