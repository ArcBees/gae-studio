/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.selenium;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.util.DebugIds;
import com.arcbees.gaestudio.companion.domain.Car;
import com.arcbees.gaestudio.companion.domain.Wheel;
import com.google.appengine.api.datastore.GeoPt;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import static org.junit.Assert.assertEquals;

public class EntityModificationIT extends SeleniumTestBase {
    private final String make = "Ford";
    private final String newMake = "Mercedes";
    private final String aNewString = "a new string";
    private final float delta = 0.0001f;

    @Test
    public void modifyCarTest() {
        long carId = createCar();

        editCar();

        assertCarIsModified(carId);
    }

    private long createCar() {
        Car car = buildCar();

        return createRemoteCar(car);
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
        navigate(NameTokens.visualizer);

        clickOnCarKind();
        clickOnEntityinTable();
        clickEditButton();
        changeCarData();
        clickSaveButton();
        waitForSaveConfirmation();
    }

    private void waitForSaveConfirmation() {
        WebElement element = waitUntilPresenceOfElementLocated(ByDebugId.id(DebugIds.MESSAGE));
        assertEquals("The save message is not correct", "Entity saved.", element.getText());
    }

    private void clickSaveButton() {
        WebElement element = waitUntilElementIsClickable(ByDebugId.id(DebugIds.SAVE));
        element.click();
    }

    private void changeCarData() {
        WebElement editor = waitUntilPresenceOfElementLocated(ByDebugId.id(DebugIds.ENTITY_EDITOR));

        changeCarString(editor);
        changeCarBooleans();
        changeCarGeoPts();
        changeCarWheels();
        changeCarMixedProperties();
    }

    private void changeCarMixedProperties() {
        WebElement mixedPropertiesContainer = waitUntilPresenceOfElementLocated(ByDebugId.id("mixedProperties"));
        WebElement targetproperty = findChild(mixedPropertiesContainer, ByDebugId.id("1"));
        WebElement textbox = targetproperty.findElement(By.tagName("input"));

        typeInTextBox(textbox, aNewString);
    }

    private void changeCarWheels() {
        WebElement wheelsContainer = waitUntilPresenceOfElementLocated(ByDebugId.id("wheels.size"));
        WebElement targetWheel = findChild(wheelsContainer, ByDebugId.id("0"));
        WebElement textbox = targetWheel.findElement(By.tagName("input"));

        typeInTextBox(textbox, "2.0");
    }

    private void changeCarGeoPts() {
        WebElement geoPtsContainer = waitUntilPresenceOfElementLocated(ByDebugId.id("geoPts"));
        WebElement targetGeoPt = findChild(geoPtsContainer, ByDebugId.id("2"));

        List<WebElement> textboxes = targetGeoPt.findElements(By.tagName("input"));

        typeInTextBox(textboxes.get(0), "25.0");
        typeInTextBox(textboxes.get(1), "50.0");
    }

    private void changeCarBooleans() {
        WebElement booleansContainer = waitUntilPresenceOfElementLocated(ByDebugId.id("booleans"));
        List<WebElement> checkboxes = booleansContainer.findElements(By.tagName("input"));

        WebElement middleCheckbox = checkboxes.get(1);
        waitUntilElementIsClickable(middleCheckbox);

        middleCheckbox.click();
    }

    private void changeCarString(WebElement editor) {
        WebElement makeTextBox = findChild(editor, By.xpath(".//input[@value='" + make + "']"));
        typeInTextBox(makeTextBox, newMake);
    }

    private void clickEditButton() {
        WebElement edit = waitUntilElementIsClickable(ByDebugId.id(DebugIds.EDIT));
        edit.click();
    }

    private void clickOnEntityinTable() {
        WebElement table = waitUntilPresenceOfElementLocated(ByDebugId.id(DebugIds.ENTITIES_TABLES));
        WebElement row = findChild(table, By.xpath(".//tr[@__gwt_row='0']"));
        row.click();
    }

    private void clickOnCarKind() {
        final WebElement kinds = waitUntilPresenceOfElementLocated(ByDebugId.id(DebugIds.KINDS));

        webDriverWait().until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver input) {
                return getCarKindCandidates(kinds).size() == 1;
            }
        });

        WebElement carKind = getCarKindCandidates(kinds).get(0);
        carKind.click();
    }

    private List<WebElement> getCarKindCandidates(WebElement kinds) {
        return kinds.findElements(By.xpath("//span[text()='" + Car.class.getSimpleName() + "']"));
    }

    private void assertCarIsModified(long carId) {
        Car saved = getRemoteCar(carId);

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
