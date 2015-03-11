/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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

        analytics.sendPageView()
                .documentPath("#" + historyToken)
                .go();
    }
}
