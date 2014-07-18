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
import com.arcbees.gaestudio.client.AnalyticsConfiguration;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.gwtplatform.mvp.client.Bootstrapper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

public class BootstrapperImpl implements Bootstrapper {
    private final PlaceManager placeManager;
    private final UniversalAnalytics universalAnalytics;

    @Inject
    BootstrapperImpl(
            PlaceManager placeManager,
            UniversalAnalytics universalAnalytics) {
        this.placeManager = placeManager;
        this.universalAnalytics = universalAnalytics;
    }

    @Override
    public void onBootstrap() {
        if (AnalyticsConfiguration.USE_COOKIE_DOMAIN_NONE) {
            universalAnalytics.create().cookieDomain("none");
        } else {
            universalAnalytics.create();
        }

        placeManager.revealPlace(new PlaceRequest.Builder().nameToken(NameTokens.visualizer).build());
    }
}
