/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.auth.forgot;

import com.arcbees.gaestudio.client.application.ApplicationPresenter;
import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.rest.AuthService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
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
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

public class ForgotPasswordPresenter extends Presenter<ForgotPasswordPresenter.MyView, ForgotPasswordPresenter.MyProxy>
        implements ForgotPasswordUiHandlers {
    interface MyView extends View, HasUiHandlers<ForgotPasswordUiHandlers> {
        void resetForm();

        void resetSubmit();
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.forgotpass)
    @NoGatekeeper
    interface MyProxy extends ProxyPlace<ForgotPasswordPresenter> {
    }

    private final AppConstants appConstants;
    private final PlaceManager placeManager;
    private final RestDispatch restDispatch;
    private final AuthService authService;

    @Inject
    ForgotPasswordPresenter(EventBus eventBus,
                            MyView view,
                            MyProxy proxy,
                            AppConstants appConstants,
                            PlaceManager placeManager,
                            RestDispatch restDispatch,
                            AuthService authService) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN);

        this.appConstants = appConstants;
        this.placeManager = placeManager;
        this.restDispatch = restDispatch;
        this.authService = authService;

        getView().setUiHandlers(this);
    }

    @Override
    public void forgotPassword(String email) {
        restDispatch.execute(authService.generateResetToken(email),
                new AsyncCallbackImpl<Void>(appConstants.unableToRegister()) {
                    @Override
                    public void onSuccess(Void result) {
                        DisplayMessageEvent.fire(ForgotPasswordPresenter.this,
                                new Message(appConstants.passwordReset(), MessageStyle.SUCCESS));
                        getView().resetSubmit();
                        redirectToAuth();
                    }

                    @Override
                    public void handleFailure(Throwable caught) {
                        getView().resetSubmit();
                    }
                });
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        getView().resetForm();
    }

    private void redirectToAuth() {
        PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.auth).build();
        placeManager.revealPlace(placeRequest);
    }
}
