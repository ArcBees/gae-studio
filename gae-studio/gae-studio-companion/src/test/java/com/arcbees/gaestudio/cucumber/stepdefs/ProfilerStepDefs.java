/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.cucumber.stepdefs;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.cucumber.page.BasePage;
import com.arcbees.gaestudio.server.rest.RestIT;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class ProfilerStepDefs {
    private final BasePage basePage;
    private final RestIT restIT;
    private final ProfilerPage profilerPage;

    @Inject
    ProfilerStepDefs(BasePage basePage,
                     RestIT restIT,
                     ProfilerPage profilerPage) {
        this.basePage = basePage;
        this.restIT = restIT;
        this.profilerPage = profilerPage;
    }

    @Given("^I start recording$")
    public void I_start_recording() throws Throwable {
        basePage.navigate(NameTokens.profiler);
        profilerPage.clickOnRecord();
        profilerPage.waitForChannelToOpen();
    }

    @And("^I interact with the datastore$")
    public void I_interact_with_the_datastore() {
        restIT.createRemoteCar();
    }

    @Then("^I should see requests in the Profiler$")
    public void I_should_see_requests_in_the_Profiler() {
        profilerPage.assertRequestIsShown();
    }
}
