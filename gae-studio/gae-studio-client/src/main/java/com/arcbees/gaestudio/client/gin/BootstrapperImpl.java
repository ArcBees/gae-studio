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

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.rest.AuthService;
import com.arcbees.gaestudio.client.util.CurrentUser;
import com.arcbees.gaestudio.shared.auth.User;
import com.google.gwt.user.client.History;
import com.gwtplatform.mvp.client.Bootstrapper;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class BootstrapperImpl implements Bootstrapper {
    private final CurrentUser currentUser;
    private final AuthService authService;
    private final PlaceManager placeManager;
    private final String unauthorizedPlace;

    @Inject
    BootstrapperImpl(CurrentUser currentUser,
                     AuthService authService,
                     PlaceManager placeManager,
                     @UnauthorizedPlace String unauthorizedPlace) {
        this.currentUser = currentUser;
        this.authService = authService;
        this.placeManager = placeManager;
        this.unauthorizedPlace = unauthorizedPlace;
    }

    @Override
    public void onBootstrap() {
        authService.checkLogin(new MethodCallback<User>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                onLoginChecked(null);
            }

            @Override
            public void onSuccess(Method method, User user) {
                onLoginChecked(user);
            }
        });
    }

    private void onLoginChecked(User user) {
        currentUser.setUser(user);
        currentUser.setLoggedIn(user != null);

        String historyToken = History.getToken();
        if (unauthorizedPlace.equals(historyToken)) {
            placeManager.revealPlace(new PlaceRequest.Builder().nameToken(NameTokens.visualizer).build());
        } else {
            placeManager.revealCurrentPlace();
        }
    }
}
