/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.license;

import com.arcbees.gaestudio.client.application.ApplicationPresenter;
import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.rest.LicenseRegistration;
import com.arcbees.gaestudio.client.rest.LicenseService;
import com.arcbees.gaestudio.client.util.CurrentUser;
import com.arcbees.gaestudio.client.util.RestCallbackImpl;
import com.arcbees.gaestudio.shared.auth.User;
import com.google.api.client.http.HttpStatusCodes;
import com.google.common.base.Strings;
import com.google.gwt.http.client.Response;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.client.RestDispatch;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

public class LicensePresenter
        extends Presenter<LicensePresenter.MyView, LicensePresenter.MyProxy> implements LicenseUiHandlers {
    interface MyView extends View, HasUiHandlers<LicenseUiHandlers> {
        String getKey();

        void showValidationError(String error);
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.license)
    interface MyProxy extends ProxyPlace<LicensePresenter> {
    }

    private final RestDispatch restDispatch;
    private final LicenseService licenseService;
    private final CurrentUser currentUser;
    private final PlaceManager placeManager;
    private final AppConstants appConstants;

    @Inject
    LicensePresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy,
            RestDispatch restDispatch,
            LicenseService licenseService,
            CurrentUser currentUser,
            PlaceManager placeManager,
            AppConstants appConstants) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN);

        this.restDispatch = restDispatch;
        this.licenseService = licenseService;
        this.currentUser = currentUser;
        this.placeManager = placeManager;
        this.appConstants = appConstants;

        getView().setUiHandlers(this);
    }

    @Override
    public void onRegister() {
        String key = getView().getKey();

        if (!Strings.isNullOrEmpty(key)) {
            LicenseRegistration licenseRegistration = getLicenseRegistration();

            restDispatch.execute(licenseService.register(licenseRegistration),
                    new RestCallbackImpl<Void>() {
                        @Override
                        public void setResponse(Response response) {
                            int statusCode = response.getStatusCode();

                            if (statusCode == HttpStatusCodes.STATUS_CODE_OK) {
                                onValidLicense();
                            } else if (statusCode == HttpStatusCodes.STATUS_CODE_FORBIDDEN) {
                                currentUser.setLicenseValid(false);
                                getView().showValidationError(appConstants.keyDoesNotExist());
                            }
                        }
                    });
        } else {
            getView().showValidationError(appConstants.enterAKey());
        }
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        User user = currentUser.getUser();

        if (user != null) {
            restDispatch.execute(licenseService.checkLicense(user.getId()), new RestCallbackImpl<Void>() {
                @Override
                public void setResponse(Response response) {
                    handleLicenseCheck(response);
                }
            });
        }
    }

    private LicenseRegistration getLicenseRegistration() {
        String key = getView().getKey();

        LicenseRegistration licenseRegistration = new LicenseRegistration();

        licenseRegistration.setUserId(currentUser.getUser().getId());
        licenseRegistration.setKey(key);

        return licenseRegistration;
    }

    private void handleLicenseCheck(Response response) {
        int statusCode = response.getStatusCode();

        if (statusCode == HttpStatusCodes.STATUS_CODE_OK) {
            onValidLicense();
        }
    }

    private void onValidLicense() {
        currentUser.setLicenseValid(true);

        DisplayMessageEvent.fire(LicensePresenter.this,
                new Message(appConstants.successfulRegistration(), MessageStyle.SUCCESS));

        PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.visualizer).build();
        placeManager.revealPlace(placeRequest);
    }
}
