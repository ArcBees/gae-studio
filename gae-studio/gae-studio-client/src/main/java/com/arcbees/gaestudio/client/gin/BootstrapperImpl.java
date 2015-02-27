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
            AppConfig appConfig,
            Analytics analytics) {
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
