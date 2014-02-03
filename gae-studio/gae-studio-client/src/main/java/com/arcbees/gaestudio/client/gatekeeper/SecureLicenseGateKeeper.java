/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
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
import com.gwtplatform.mvp.client.proxy.Gatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

public class SecureLicenseGateKeeper implements Gatekeeper {
    private final PlaceManager placeManager;
    private final CurrentUser currentUser;

    @Inject
    SecureLicenseGateKeeper(PlaceManager placeManager,
                            CurrentUser currentUser) {
        this.placeManager = placeManager;
        this.currentUser = currentUser;
    }

    @Override
    public boolean canReveal() {
        String currentNameToken = placeManager.getCurrentPlaceRequest().getNameToken();

        return NameTokens.license.equals(currentNameToken) || currentUser.isLicenseValid();
    }
}
