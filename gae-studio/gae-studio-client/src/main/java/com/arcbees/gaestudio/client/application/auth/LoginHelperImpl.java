/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.auth;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.rest.LicenseService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.client.util.CurrentUser;
import com.arcbees.gaestudio.shared.auth.User;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.shared.RestDispatch;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

public class LoginHelperImpl implements LoginHelper, HasHandlers {
    private final EventBus eventBus;
    private final RestDispatch restDispatch;
    private final LicenseService licenseService;
    private final CurrentUser currentUser;
    private final PlaceManager placeManager;
    private final AppConstants appConstants;

    @Inject
    LoginHelperImpl(EventBus eventBus,
                    RestDispatch restDispatch,
                    LicenseService licenseService,
                    CurrentUser currentUser,
                    PlaceManager placeManager,
                    AppConstants appConstants) {
        this.eventBus = eventBus;
        this.restDispatch = restDispatch;
        this.licenseService = licenseService;
        this.currentUser = currentUser;
        this.placeManager = placeManager;
        this.appConstants = appConstants;
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        eventBus.fireEventFromSource(event, this);
    }

    @Override
    public void login(User user) {
        DisplayMessageEvent.fire(this, new Message(appConstants.loggedInSuccessfully(), MessageStyle.SUCCESS));

        currentUser.setUser(user);
        currentUser.setLoggedIn(true);

        checkLicense(new PlaceRequest.Builder().nameToken(NameTokens.visualizer).build());
    }

    @Override
    public void checkLicense(final PlaceRequest placeToReveal) {
        restDispatch.execute(licenseService.checkLicense(currentUser.getUserId()), new AsyncCallbackImpl<Void>() {
            @Override
            public void handleFailure(Throwable throwable) {
                currentUser.setLicenseValid(false);
                navigate(placeToReveal);
            }

            @Override
            public void onSuccess(Void aVoid) {
                currentUser.setLicenseValid(true);
                navigate(placeToReveal);
            }
        });
    }

    private void navigate(PlaceRequest placeToReveal) {
        placeManager.revealPlace(placeToReveal);
    }
}
