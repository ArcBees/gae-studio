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
