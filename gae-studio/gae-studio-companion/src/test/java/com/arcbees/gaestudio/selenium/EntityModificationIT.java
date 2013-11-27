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
import java.util.Set;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.util.DebugIds;
import com.arcbees.gaestudio.companion.domain.Car;
import com.google.common.base.Predicate;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class EntityModificationIT extends SeleniumTestBase {
    private final String make = "Ford";
    private final String newMake = "Mercedes";

    @Test
    public void modifyCarTest() {
        long carId = createCar();

        editCar();

        assertCarIsModified(carId);
    }

    private void assertCarIsModified(long carId) {
        Car saved = getRemoteCar(carId);

        assertEquals("Car make is not saved", newMake, saved.getMake());
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
        return kinds.findElements(By.xpath("//span[text()='Car']"));
    }

    private long createCar() {
        Car car = new Car();
        car.setMake(make);

        Long carId = createRemoteCar(car);

        makeSureCarKindExists();

        return carId;
    }

    private void makeSureCarKindExists() {
        Set<String> kinds = getRemoteKindsResponse();

        assertThat("Car kind not found", kinds, hasItem("Car"));
    }
}
