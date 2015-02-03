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

import com.arcbees.analytics.shared.Analytics;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.shared.config.AppConfig;
import com.gwtplatform.mvp.client.Bootstrapper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

public class BootstrapperImpl implements Bootstrapper {
    private final PlaceManager placeManager;
    private final Analytics analytics;
    private final AppConfig appConfig;

    @Inject
    BootstrapperImpl(
            PlaceManager placeManager,
            Analytics analytics,
            AppConfig appConfig) {
        this.placeManager = placeManager;
        this.analytics = analytics;
        this.appConfig = appConfig;
    }

    @Override
    public void onBootstrap() {
        if (appConfig.isUseCookieDomainNone()) {
            analytics.create().cookieDomain("none");
        } else {
            analytics.create();
        }

        placeManager.revealPlace(new PlaceRequest.Builder().nameToken(NameTokens.visualizer).build());
    }
}
