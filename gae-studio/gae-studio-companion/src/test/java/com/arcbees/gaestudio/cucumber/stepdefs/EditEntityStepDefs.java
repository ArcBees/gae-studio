/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.cucumber.stepdefs;

import java.util.List;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.companion.domain.Car;
import com.arcbees.gaestudio.companion.domain.Wheel;
import com.arcbees.gaestudio.cucumber.page.BasePage;
import com.arcbees.gaestudio.cucumber.page.VisualizerPage;
import com.arcbees.gaestudio.server.rest.RestIT;
import com.google.appengine.api.datastore.GeoPt;
import com.google.common.collect.Lists;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertEquals;

public class EditEntityStepDefs {
    private final String make = "Ford";
    private final String newMake = "Mercedes";
    private final String aNewString = "a new string";
    private final float delta = 0.0001f;
    private final RestIT restIT;
    private final BasePage basePage;
    private final VisualizerPage visualizerPage;

    private long carId;

    @Inject
    EditEntityStepDefs(RestIT restIT,
                       BasePage basePage,
                       VisualizerPage visualizerPage) {
        this.basePage = basePage;
        this.restIT = restIT;
        this.visualizerPage = visualizerPage;
    }

    @Given("^I create a Car$")
    public void I_create_a_Car() throws Throwable {
        carId = createCar();
    }

    @And("^I modify the Car$")
    public void I_modify_the_Car() throws Throwable {
        editCar();
    }

    @Then("^the Car should be modified$")
    public void the_Car_should_be_modified() throws Throwable {
        assertCarIsModified(carId);
    }

    private long createCar() {
        Car car = buildCar();

        return restIT.createRemoteCar(car);
    }

    private Car buildCar() {
        Car car = new Car();
        car.setMake(make);
        car.setBooleans(Lists.newArrayList(true, true, true));
        car.setGeoPts(Lists.newArrayList(new GeoPt(10.5f, 10.5f), new GeoPt(0.0f, 0.0f),
                new GeoPt(-10.12345f, -10.12345f)));
        car.setWheels(Lists.newArrayList(new Wheel(5.25), new Wheel(10.0), new Wheel(20.75)));

        List<Object> mixedProperties = Lists.<Object>newArrayList(1, "some string", new GeoPt(10.0f, 10.0f));
        car.setMixedProperties(mixedProperties);

        return car;
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
