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
import javax.ws.rs.core.Response;

import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.companion.domain.Car;
import com.arcbees.gaestudio.cucumber.page.BasePage;
import com.arcbees.gaestudio.cucumber.page.VisualizerPage;
import com.arcbees.gaestudio.factories.CarFactory;
import com.arcbees.gaestudio.server.api.RestIT;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertEquals;

public class DeleteEntityStepDefs {
    private final BasePage basePage;
    private final VisualizerPage visualizerPage;
    private final RestIT restIT;
    private final CarFactory carFactory;

    private Long carId;

    @Inject
    DeleteEntityStepDefs(RestIT restIT,
            BasePage basePage,
            VisualizerPage visualizerPage,
            CarFactory carFactory) {
        this.restIT = restIT;
        this.basePage = basePage;
        this.visualizerPage = visualizerPage;
        this.carFactory = carFactory;
    }

    @Given("^I create an entity for deletion$")
    public void I_create_an_entity() throws Throwable {
        Car car = carFactory.createFakeCar();
        carId = restIT.createRemoteCar(car);
    }

    @Then("^the entity should be deleted$")
    public void the_entity_should_be_deleted() throws Throwable {
        assertEntityIsDeleted(carId);
    }

    @And("^I delete the entity")
    public void I_delete_the_entity() throws Throwable {
        deleteEntity();
    }

    private void assertEntityIsDeleted(long carId) {
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), restIT.getRemoteCarResponseCode(carId).getStatusCode());
    }

    private void deleteEntity() {
        basePage.navigate(NameTokens.visualizer);

        visualizerPage.clickOnKind(Car.class.getSimpleName());
        visualizerPage.clickOnEntityInTable();
        visualizerPage.clickDeleteButton();
        visualizerPage.clickDeleteConfirmButton();

        basePage.waitForSaveConfirmation("Entity deleted");
    }
}
