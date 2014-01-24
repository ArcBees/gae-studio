/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.gin;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.rest.AuthService;
import com.arcbees.gaestudio.client.rest.LicenseService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.client.util.CurrentUser;
import com.arcbees.gaestudio.shared.auth.User;
import com.google.gwt.user.client.History;
import com.gwtplatform.dispatch.rest.shared.RestDispatch;
import com.gwtplatform.mvp.client.Bootstrapper;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class BootstrapperImpl implements Bootstrapper {
    private final CurrentUser currentUser;
    private final AuthService authService;
    private final PlaceManager placeManager;
    private final RestDispatch restDispatch;
    private final String unauthorizedPlace;
    private final LicenseService licenseService;

    @Inject
    BootstrapperImpl(CurrentUser currentUser,
                     AuthService authService,
                     PlaceManager placeManager,
                     RestDispatch restDispatch,
                     LicenseService licenseService,
                     @UnauthorizedPlace String unauthorizedPlace) {
        this.currentUser = currentUser;
        this.authService = authService;
        this.placeManager = placeManager;
        this.restDispatch = restDispatch;
        this.unauthorizedPlace = unauthorizedPlace;
        this.licenseService = licenseService;
    }

    @Override
    public void onBootstrap() {
        restDispatch.execute(authService.checkLogin(), new AsyncCallbackImpl<User>() {
            @Override
            public void handleFailure(Throwable exception) {
                onLoginChecked(null);
            }

            @Override
            public void onSuccess(User user) {
                onLoginChecked(user);
            }
        });
    }

    private void onLoginChecked(User user) {
        currentUser.setUser(user);
        currentUser.setLoggedIn(user != null);

        if (user != null) {
            restDispatch.execute(licenseService.checkLicense(user.getId()), new AsyncCallbackImpl<Void>() {
                @Override
                public void handleFailure(Throwable throwable) {
                    currentUser.setLicenseValid(false);
                    navigate();
                }

                @Override
                public void onSuccess(Void aVoid) {
                    currentUser.setLicenseValid(true);
                    navigate();
                }
            });
        } else {
            currentUser.setLicenseValid(false);
            navigate();
        }
    }

    private void navigate() {
        String historyToken = History.getToken();
        if (unauthorizedPlace.equals(historyToken)) {
            placeManager.revealPlace(new PlaceRequest.Builder().nameToken(NameTokens.visualizer).build());
        } else {
            placeManager.revealCurrentPlace();
        }
    }
}
