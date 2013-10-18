/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.gatekeeper;

import java.util.Date;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.shared.ExpirationDate;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

public class LicenseGateKeeper implements Gatekeeper {
    private final PlaceManager placeManager;
    private final Date expirationDate;

    @Inject
    LicenseGateKeeper(PlaceManager placeManager,
                      @ExpirationDate Date expirationDate) {
        this.placeManager = placeManager;
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean canReveal() {
        String currentNameToken = placeManager.getCurrentPlaceRequest().getNameToken();

        return NameTokens.licenseExpired.equals(currentNameToken) || new Date().getTime() < expirationDate.getTime();
    }
}
