/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.analytics;

import javax.inject.Inject;

import com.arcbees.analytics.shared.Analytics;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.NavigationEvent;
import com.gwtplatform.mvp.client.proxy.NavigationHandler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

public class GoogleAnalyticsNavigationTracker implements NavigationHandler {
    private final PlaceManager placeManager;
    private final Analytics analytics;

    @Inject
    GoogleAnalyticsNavigationTracker(
            PlaceManager placeManager,
            EventBus eventBus,
            Analytics analytics) {
        this.placeManager = placeManager;
        this.analytics = analytics;

        eventBus.addHandler(NavigationEvent.getType(), this);
    }

    @Override
    public void onNavigation(NavigationEvent navigationEvent) {
        String historyToken = placeManager.buildHistoryToken(navigationEvent.getRequest());

        analytics.sendPageView().documentPath("#" + historyToken);
    }
}
