/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.cucumber.guice;

import javax.inject.Singleton;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.arcbees.gaestudio.cucumber.setupteardown.CucumberTestSetupTeardown;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class CucumberModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CucumberTestSetupTeardown.class).asEagerSingleton();
    }

    @Provides
    @Singleton
    WebDriver provideWebDriver() {
        return new ChromeDriver();
    }
}
