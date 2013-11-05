package com.arcbees.gaestudio.server.license;

import com.google.inject.AbstractModule;

public class LicenseModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(LicenseFilter.class);
        bind(LicenseChecker.class).to(LicenseCheckerImpl.class);
    }
}
