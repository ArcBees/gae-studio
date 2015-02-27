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
import com.arcbees.gaestudio.companion.domain.Car;
import com.arcbees.gaestudio.cucumber.page.BasePage;
import com.arcbees.gaestudio.cucumber.page.VisualizerPage;
import com.arcbees.gaestudio.factories.CarFactory;
import com.arcbees.gaestudio.server.api.RestIT;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertEquals;

public class EditEntityStepDefs {
    private static final String NEW_MAKE = "Mercedes";
    private static final String A_NEW_STRING = "a new string";
    private static final float DELTA = 0.0001f;
    private static final String MAKE = "Ford";

    private final RestIT restIT;
    private final BasePage basePage;
    private final VisualizerPage visualizerPage;
    private final CarFactory carFactory;

    private long carId;

    @Inject
    EditEntityStepDefs(RestIT restIT,
            BasePage basePage,
            VisualizerPage visualizerPage,
            CarFactory carFactory) {
        this.basePage = basePage;
        this.restIT = restIT;
        this.visualizerPage = visualizerPage;
        this.carFactory = carFactory;
    }

    @Given("^I create an entity$")
    public void I_create_an_entity() throws Throwable {
        Car car = carFactory.createFakeCar();
        carId = restIT.createRemoteCar(car);
    }

    @And("^I modify the entity")
    public void I_modify_the_entity() throws Throwable {
        editCar();
    }

    @Then("^the entity should be modified$")
    public void the_entity_should_be_modified() throws Throwable {
        assertCarIsModified(carId);
    }

    private void editCar() {
        basePage.navigate(NameTokens.visualizer);

        visualizerPage.clickOnKind(Car.class.getSimpleName());
        visualizerPage.clickOnEntityInTable();
        visualizerPage.clickEditButton();

        changeCarData();

        visualizerPage.clickSaveButton();

        basePage.waitForSaveConfirmation("Entity saved.");
    }

    private void changeCarData() {
        visualizerPage.changeCarString(MAKE, NEW_MAKE);
        visualizerPage.changeCarBooleans();
        visualizerPage.changeCarGeoPts();
        visualizerPage.changeCarWheels();
        visualizerPage.changeCarMixedProperties(A_NEW_STRING);
    }

    private void assertCarIsModified(long carId) {
        Car saved = restIT.getRemoteCar(carId);

        checkMake(saved);
        checkBooleans(saved);
        checkGeoPts(saved);
        checkWheels(saved);
        checkMixedProperties(saved);
    }

    private void checkMixedProperties(Car saved) {
        assertEquals(A_NEW_STRING, saved.getMixedProperties().get(1));
    }

    private void checkWheels(Car saved) {
        assertEquals(2.0f, saved.getWheels().get(0).getSize(), DELTA);
    }

    private void checkGeoPts(Car saved) {
        assertEquals(25.0f, saved.getGeoPts().get(2).getLatitude(), DELTA);
        assertEquals(50.0f, saved.getGeoPts().get(2).getLongitude(), DELTA);
    }

    private void checkBooleans(Car saved) {
        assertEquals(true, saved.getBooleans().get(0));
        assertEquals(false, saved.getBooleans().get(1));
        assertEquals(true, saved.getBooleans().get(2));
    }

    private void checkMake(Car saved) {
        assertEquals("Car make is not saved", NEW_MAKE, saved.getMake());
    }
}
