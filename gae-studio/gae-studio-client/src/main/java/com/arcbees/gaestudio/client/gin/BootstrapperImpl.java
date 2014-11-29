/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.gin;

import javax.inject.Inject;

import com.arcbees.analytics.client.universalanalytics.UniversalAnalytics;
import com.arcbees.gaestudio.client.application.auth.LoginHelper;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.rest.AuthService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.client.util.CurrentUser;
import com.arcbees.gaestudio.shared.auth.User;
import com.arcbees.gaestudio.shared.config.AppConfig;
import com.google.common.base.Strings;
import com.google.gwt.user.client.History;
import com.gwtplatform.dispatch.rest.shared.RestDispatch;
import com.gwtplatform.mvp.client.Bootstrapper;
import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.gwtplatform.mvp.shared.proxy.TokenFormatter;

public class BootstrapperImpl implements Bootstrapper {
    private final PlaceManager placeManager;
    private final UniversalAnalytics universalAnalytics;
    private final AppConfig appConfig;
    private final CurrentUser currentUser;
    private final AuthService authService;
    private final RestDispatch restDispatch;
    private final LoginHelper loginHelper;
    private final TokenFormatter tokenFormatter;
    private final String defaultPlaceNameToken;
    private final String unauthorizedPlace;

    @Inject
    BootstrapperImpl(
            PlaceManager placeManager,
            UniversalAnalytics universalAnalytics,
            AppConfig appConfig,
            CurrentUser currentUser,
            AuthService authService,
            RestDispatch restDispatch,
            LoginHelper loginHelper,
            TokenFormatter tokenFormatter,
            @DefaultPlace String defaultPlaceNameToken,
            @UnauthorizedPlace String unauthorizedPlace) {
        this.placeManager = placeManager;
        this.universalAnalytics = universalAnalytics;
        this.appConfig = appConfig;
        this.currentUser = currentUser;
        this.authService = authService;
        this.restDispatch = restDispatch;
        this.loginHelper = loginHelper;
        this.tokenFormatter = tokenFormatter;
        this.defaultPlaceNameToken = defaultPlaceNameToken;
        this.unauthorizedPlace = unauthorizedPlace;
    }

    @Override
    public void onBootstrap() {
        if (appConfig.isUseCookieDomainNone()) {
            universalAnalytics.create().cookieDomain("none");
        } else {
            universalAnalytics.create();
        }

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

        PlaceRequest placeRequestToReveal = getPlaceRequestToReveal();
        if (user != null) {
            loginHelper.checkLicense(placeRequestToReveal);
        } else {
            currentUser.setLicenseValid(false);
            placeManager.revealPlace(placeRequestToReveal);
        }
    }

    private PlaceRequest getPlaceRequestToReveal() {
        String historyToken = History.getToken();

        PlaceRequest placeRequest;
        if (unauthorizedPlace.equals(historyToken)) {
            placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.visualizer).build();
        } else {
            if (Strings.isNullOrEmpty(historyToken)) {
                placeRequest = new PlaceRequest.Builder().nameToken(defaultPlaceNameToken).build();
            } else {
                placeRequest = tokenFormatter.toPlaceRequest(historyToken);
            }
        }

        return placeRequest;
    }
}
