/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.cucumber.stepdefs.setupteardown;

import javax.inject.Inject;

import org.openqa.selenium.WebDriver;

import com.arcbees.gaestudio.server.api.RestIT;

import cucumber.api.java.After;
import cucumber.api.java.Before;

public class CucumberTestSetupTeardown {
    private final WebDriver webDriver;
    private final RestIT restIT;

    @Inject
    CucumberTestSetupTeardown(WebDriver webDriver,
            RestIT restIT) {
        this.webDriver = webDriver;
        this.restIT = restIT;
    }

    @Before
    public void before() {
        restIT.clearDatabase();
    }

    @After
    public void after() {
        webDriver.quit();
    }
}
