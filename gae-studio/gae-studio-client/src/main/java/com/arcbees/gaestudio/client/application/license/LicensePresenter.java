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
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.rest.LicenseRegistration;
import com.arcbees.gaestudio.client.rest.LicenseService;
import com.arcbees.gaestudio.client.util.CurrentUser;
import com.arcbees.gaestudio.shared.auth.User;
import com.google.api.client.http.HttpStatusCodes;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
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

        void setKeyEntrySectionVisible(boolean b);

        String getKey();
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.license)
    interface MyProxy extends ProxyPlace<LicensePresenter> {
    }

    private final LicenseService licenseService;
    private final CurrentUser currentUser;
    private final PlaceManager placeManager;

    @Inject
    LicensePresenter(EventBus eventBus,
                     MyView view,
                     MyProxy proxy,
                     LicenseService licenseService,
                     CurrentUser currentUser,
                     PlaceManager placeManager) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.licenseService = licenseService;
        this.currentUser = currentUser;
        this.placeManager = placeManager;

        getView().setUiHandlers(this);
    }

    @Override
    public void onRegister() {
        String key = getView().getKey();

        LicenseRegistration licenseRegistration = new LicenseRegistration();
        licenseRegistration.setUserId(currentUser.getUser().getId());
        licenseRegistration.setKey(key);

        licenseService.register(licenseRegistration, new MethodCallback<Void>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("Failed registration");
                currentUser.setLicenseValid(false);
            }

            @Override
            public void onSuccess(Method method, Void aVoid) {
                Window.alert("Successfully registered. Thank you!");
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
        getView().showMessage("Invalid key: " + error);
        getView().setKeyEntrySectionVisible(true);
    }

    private void displayValidLicenseMessage() {
        getView().showMessage("Your license is valid.");
        getView().setKeyEntrySectionVisible(false);
    }
}
