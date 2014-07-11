package com.arcbees.gaestudio.client.application.analytics;

import javax.inject.Inject;

import com.arcbees.analytics.client.universalanalytics.UniversalAnalytics;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.NavigationEvent;
import com.gwtplatform.mvp.client.proxy.NavigationHandler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

public class GoogleAnalyticsNavigationTracker implements NavigationHandler {
    private final PlaceManager placeManager;
    private final UniversalAnalytics analytics;

    @Inject
    GoogleAnalyticsNavigationTracker(
            PlaceManager placeManager,
            EventBus eventBus,
            UniversalAnalytics analytics) {
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
