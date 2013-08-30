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

import com.arcbees.gaestudio.shared.ExpirationDate;
import com.gwtplatform.mvp.client.annotations.DefaultGatekeeper;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;

@DefaultGatekeeper
public class LicenseGateKeeper implements Gatekeeper{
    private final Date expirationDate;

    @Inject
    LicenseGateKeeper(@ExpirationDate Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean canReveal() {
        return new Date().getTime() < expirationDate.getTime();
    }
}
