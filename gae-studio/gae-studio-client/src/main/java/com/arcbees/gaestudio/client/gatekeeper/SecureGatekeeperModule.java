/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.gatekeeper;

import com.google.gwt.inject.client.AbstractGinModule;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;

public class SecureGatekeeperModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(Gatekeeper.class).annotatedWith(LicenseGateKeeper.class).to(SecureLicenseGateKeeper.class);
    }
}
