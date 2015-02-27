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

package com.arcbees.gaestudio.cucumber.stepdefs;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.cucumber.page.BasePage;
import com.arcbees.gaestudio.cucumber.page.ProfilerPage;
import com.arcbees.gaestudio.factories.CarFactory;
import com.arcbees.gaestudio.server.api.RestIT;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class ProfilerStepDefs {
    private final BasePage basePage;
    private final RestIT restIT;
    private final ProfilerPage profilerPage;
    private final CarFactory carFactory;

    @Inject
    ProfilerStepDefs(BasePage basePage,
            RestIT restIT,
            ProfilerPage profilerPage,
            CarFactory carFactory) {
        this.basePage = basePage;
        this.restIT = restIT;
        this.profilerPage = profilerPage;
        this.carFactory = carFactory;
    }

    @Given("^I start recording$")
    public void I_start_recording() throws Throwable {
        basePage.navigate(NameTokens.profiler);
        profilerPage.clickOnRecord();
        profilerPage.waitForChannelToOpen();
    }

    @And("^I interact with the datastore$")
    public void I_interact_with_the_datastore() {
        restIT.createRemoteCar(carFactory.createFakeCar());
    }

    @Then("^I should see requests in the Profiler$")
    public void I_should_see_requests_in_the_Profiler() {
        profilerPage.assertRequestIsShown();
    }
}
