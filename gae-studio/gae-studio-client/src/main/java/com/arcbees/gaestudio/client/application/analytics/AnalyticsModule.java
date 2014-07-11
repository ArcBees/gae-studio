package com.arcbees.gaestudio.client.application.analytics;

import com.arcbees.analytics.client.universalanalytics.UniversalAnalyticsModule;
import com.google.gwt.inject.client.AbstractGinModule;

public class AnalyticsModule extends AbstractGinModule {
    @Override
    protected void configure() {
        install(new UniversalAnalyticsModule.Builder("UA-41550930-10").autoCreate(false).build());
        bind(GoogleAnalyticsNavigationTracker.class).asEagerSingleton();
    }
}
