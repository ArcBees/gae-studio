/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.guice;

import com.arcbees.gaestudio.server.license.LicenseFilter;
import com.arcbees.gaestudio.server.license.LicenseModule;
import com.google.inject.AbstractModule;

public class SecureLicenseModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new LicenseModule());
        bind(LicenseFilter.class);
    }
}
