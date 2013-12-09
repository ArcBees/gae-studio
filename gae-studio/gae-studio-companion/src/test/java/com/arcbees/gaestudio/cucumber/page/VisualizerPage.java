/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.cucumber.page;

import java.util.List;

import javax.inject.Inject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.arcbees.gaestudio.client.util.DebugIds;
import com.arcbees.gaestudio.selenium.ByDebugId;
import com.arcbees.gaestudio.selenium.WebDriverHelper;
import com.google.common.base.Predicate;

public class VisualizerPage {
    private final WebDriverHelper webDriverHelper;

    @Inject
    VisualizerPage(WebDriverHelper webDriverHelper) {
        this.webDriverHelper = webDriverHelper;
    }

    public void clickSaveButton() {
        WebElement element = webDriverHelper.waitUntilElementIsClickable(ByDebugId.id(DebugIds.SAVE));
        element.click();
    }

    public void changeCarString(String make, String newMake) {
        WebElement editor = webDriverHelper.waitUntilPresenceOfElementLocated(ByDebugId.id(DebugIds.ENTITY_EDITOR));
        WebElement makeTextBox = webDriverHelper.findChild(editor, By.xpath(".//input[@value='" + make + "']"));
        webDriverHelper.typeInTextBox(makeTextBox, newMake);
    }

    public void changeCarBooleans() {
        WebElement booleansContainer = webDriverHelper.waitUntilPresenceOfElementLocated(ByDebugId.id("booleans"));
        List<WebElement> checkboxes = booleansContainer.findElements(By.tagName("input"));

        WebElement middleCheckbox = checkboxes.get(1);
        webDriverHelper.waitUntilElementIsClickable(middleCheckbox);

        middleCheckbox.click();
    }

    public void changeCarGeoPts() {
        WebElement geoPtsContainer = webDriverHelper.waitUntilPresenceOfElementLocated(ByDebugId.id("geoPts"));
        WebElement targetGeoPt = webDriverHelper.findChild(geoPtsContainer, ByDebugId.id("2"));

        List<WebElement> textboxes = targetGeoPt.findElements(By.tagName("input"));

        webDriverHelper.typeInTextBox(textboxes.get(0), "25.0");
        webDriverHelper.typeInTextBox(textboxes.get(1), "50.0");
    }

    public void changeCarWheels() {
        WebElement wheelsContainer = webDriverHelper.waitUntilPresenceOfElementLocated(ByDebugId.id("wheels.size"));
        WebElement targetWheel = webDriverHelper.findChild(wheelsContainer, ByDebugId.id("0"));
        WebElement textbox = targetWheel.findElement(By.tagName("input"));

        webDriverHelper.typeInTextBox(textbox, "2.0");
    }

    public void clickOnEntityinTable() {
        WebElement table = webDriverHelper.waitUntilPresenceOfElementLocated(ByDebugId.id(DebugIds.ENTITIES_TABLES));
        WebElement row = webDriverHelper.findChild(table, By.xpath(".//tr[@__gwt_row='0']"));
        row.click();
    }

    public void clickOnKind(final String kindToFind) {
        final WebElement allKinds = webDriverHelper.waitUntilPresenceOfElementLocated(ByDebugId.id(DebugIds.KINDS));

        webDriverHelper.webDriverWait().until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver input) {
                return getKindWebElements(allKinds, kindToFind).size() == 1;
            }
        });

        WebElement targetKind = getKindWebElements(allKinds, kindToFind).get(0);
        targetKind.click();
    }

    public void clickEditButton() {
        WebElement edit = webDriverHelper.waitUntilElementIsClickable(ByDebugId.id(DebugIds.EDIT));
        edit.click();
    }

    public void changeCarMixedProperties(String aNewString) {
        WebElement mixedPropertiesContainer = webDriverHelper.waitUntilPresenceOfElementLocated(ByDebugId.id
                ("mixedProperties"));
        WebElement targetproperty = webDriverHelper.findChild(mixedPropertiesContainer, ByDebugId.id("1"));
        WebElement textbox = targetproperty.findElement(By.tagName("input"));

        webDriverHelper.typeInTextBox(textbox, aNewString);
    }

    private List<WebElement> getKindWebElements(WebElement allKinds, String kindToFind) {
        return allKinds.findElements(By.xpath("//span[text()='" + kindToFind + "']"));
    }
}
