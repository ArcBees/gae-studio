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

import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.rest.AuthService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.client.util.CurrentUser;
import com.arcbees.gaestudio.shared.auth.User;
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
    private final CurrentUser currentUser;
    private final AuthService authService;
    private final PlaceManager placeManager;
    private final RestDispatch restDispatch;
    private final TokenFormatter tokenFormatter;
    private final String defaultPlaceNameToken;
    private final String unauthorizedPlace;

    @Inject
    BootstrapperImpl(CurrentUser currentUser,
                     AuthService authService,
                     PlaceManager placeManager,
                     RestDispatch restDispatch,
                     TokenFormatter tokenFormatter,
                     @DefaultPlace String defaultPlaceNameToken,
                     @UnauthorizedPlace String unauthorizedPlace) {
        this.currentUser = currentUser;
        this.authService = authService;
        this.placeManager = placeManager;
        this.restDispatch = restDispatch;
        this.tokenFormatter = tokenFormatter;
        this.defaultPlaceNameToken = defaultPlaceNameToken;
        this.unauthorizedPlace = unauthorizedPlace;
    }

    @Override
    public void onBootstrap() {
        placeManager.revealPlace(new PlaceRequest.Builder().nameToken(NameTokens.visualizer).build());
    }
}
