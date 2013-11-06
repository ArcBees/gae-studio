/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.license;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.arcbees.gaestudio.client.application.ApplicationPresenter;
import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.resources.AppMessages;
import com.arcbees.gaestudio.client.rest.LicenseRegistration;
import com.arcbees.gaestudio.client.rest.LicenseService;
import com.arcbees.gaestudio.client.util.CurrentUser;
import com.arcbees.gaestudio.shared.auth.User;
import com.google.api.client.http.HttpStatusCodes;
import com.google.gwt.http.client.Response;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

public class LicensePresenter
        extends Presenter<LicensePresenter.MyView, LicensePresenter.MyProxy> implements LicenseUiHandlers {
    interface MyView extends View, HasUiHandlers<LicenseUiHandlers> {
        void showMessage(String message);

        void setKeyEntrySectionVisible(boolean visible);

        String getKey();
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.license)
    interface MyProxy extends ProxyPlace<LicensePresenter> {
    }

    private final LicenseService licenseService;
    private final CurrentUser currentUser;
    private final PlaceManager placeManager;
    private final AppConstants appConstants;
    private final AppMessages appMessages;

    @Inject
    LicensePresenter(EventBus eventBus,
                     MyView view,
                     MyProxy proxy,
                     LicenseService licenseService,
                     CurrentUser currentUser,
                     PlaceManager placeManager,
                     AppConstants appConstants,
                     AppMessages appMessages) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.licenseService = licenseService;
        this.currentUser = currentUser;
        this.placeManager = placeManager;
        this.appConstants = appConstants;
        this.appMessages = appMessages;

        getView().setUiHandlers(this);
    }

    @Override
    public void onRegister() {
        LicenseRegistration licenseRegistration = getLicenseRegistration();

        licenseService.register(licenseRegistration, new MethodCallback<Void>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                DisplayMessageEvent.fire(LicensePresenter.this,
                        new Message(appConstants.failedRegistration(), MessageStyle.ERROR));
                currentUser.setLicenseValid(false);
            }

            @Override
            public void onSuccess(Method method, Void aVoid) {
                DisplayMessageEvent.fire(LicensePresenter.this,
                        new Message(appConstants.successfulRegistration(), MessageStyle.SUCCESS));
                currentUser.setLicenseValid(true);
                placeManager.revealDefaultPlace();
            }
        });
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        User user = currentUser.getUser();

        if (user != null) {
            licenseService.checkLicense(user.getId(), new MethodCallback<Void>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    handleLicenseCheck(method);
                }

                @Override
                public void onSuccess(Method method, Void aVoid) {
                    handleLicenseCheck(method);
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

    private void handleLicenseCheck(Method method) {
        Response response = method.getResponse();
        int statusCode = response.getStatusCode();

        if (statusCode == HttpStatusCodes.STATUS_CODE_OK) {
            displayValidLicenseMessage();
        } else if (statusCode == HttpStatusCodes.STATUS_CODE_FORBIDDEN) {
            displayInvalidLicenseMessage(response.getText());
        }
    }

    private void displayInvalidLicenseMessage(String error) {
        getView().showMessage(appMessages.invalidKey(error));
        getView().setKeyEntrySectionVisible(true);
    }

    private void displayValidLicenseMessage() {
        getView().showMessage(appConstants.validLicense());
        getView().setKeyEntrySectionVisible(false);
    }
}
