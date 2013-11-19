/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.gatekeeper;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.util.CurrentUser;
import com.gwtplatform.mvp.client.annotations.DefaultGatekeeper;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

@DefaultGatekeeper
public class LoggedInGatekeeper implements Gatekeeper {
    private final CurrentUser currentUser;
    private final LicenseGateKeeper licenseGateKeeper;
    private final PlaceManager placeManager;

    @Inject
    LoggedInGatekeeper(CurrentUser currentUser,
                       LicenseGateKeeper licenseGateKeeper,
                       PlaceManager placeManager) {
        this.currentUser = currentUser;
        this.licenseGateKeeper = licenseGateKeeper;
        this.placeManager = placeManager;
    }

    @Override
    public boolean canReveal() {
        boolean loggedIn = currentUser.isLoggedIn();

        if (loggedIn && !licenseGateKeeper.canReveal()) {
            PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.license).build();
            placeManager.revealPlace(placeRequest);
        }

        return loggedIn;
    }
}