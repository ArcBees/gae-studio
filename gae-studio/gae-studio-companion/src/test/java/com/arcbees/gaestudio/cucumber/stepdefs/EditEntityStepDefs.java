/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.cucumber.stepdefs;

import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.companion.domain.Car;
import com.arcbees.gaestudio.cucumber.page.BasePage;
import com.arcbees.gaestudio.cucumber.page.VisualizerPage;
import com.arcbees.gaestudio.factories.CarFactory;
import com.arcbees.gaestudio.server.rest.RestIT;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

public class EditEntityStepDefs {
    private final String newMake = "Mercedes";
    private final String aNewString = "a new string";
    private final float delta = 0.0001f;
    private final RestIT restIT;
    private final BasePage basePage;
    private final VisualizerPage visualizerPage;

    private long carId;
    private CarFactory carFactory;

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

    @Given("^I create a Car$")
    public void I_create_a_Car() throws Throwable {
        Car car = carFactory.createFakeCar();
        carId = restIT.createRemoteCar(car);
    }

    @And("^I modify the Car$")
    public void I_modify_the_Car() throws Throwable {
        editCar();
    }

    @Then("^the Car should be modified$")
    public void the_Car_should_be_modified() throws Throwable {
        assertCarIsModified(carId);
    }

    private void editCar() {
        basePage.navigate(NameTokens.visualizer);

        visualizerPage.clickOnKind(Car.class.getSimpleName());
        visualizerPage.clickOnEntityinTable();
        visualizerPage.clickEditButton();
        changeCarData();
        visualizerPage.clickSaveButton();
        basePage.waitForSaveConfirmation("Entity saved.");
    }

    private void changeCarData() {
        String make = "Ford";
        visualizerPage.changeCarString(make, newMake);
        visualizerPage.changeCarBooleans();
        visualizerPage.changeCarGeoPts();
        visualizerPage.changeCarWheels();
        visualizerPage.changeCarMixedProperties(aNewString);
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
        assertEquals(aNewString, saved.getMixedProperties().get(1));
    }

    private void checkWheels(Car saved) {
        assertEquals(2.0f, saved.getWheels().get(0).getSize(), delta);
    }

    private void checkGeoPts(Car saved) {
        assertEquals(25.0f, saved.getGeoPts().get(2).getLatitude(), delta);
        assertEquals(50.0f, saved.getGeoPts().get(2).getLongitude(), delta);
    }

    private void checkBooleans(Car saved) {
        assertEquals(true, saved.getBooleans().get(0));
        assertEquals(false, saved.getBooleans().get(1));
        assertEquals(true, saved.getBooleans().get(2));
    }

    private void checkMake(Car saved) {
        assertEquals("Car make is not saved", newMake, saved.getMake());
    }
}
