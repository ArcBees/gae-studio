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

package com.arcbees.gaestudio.cucumber.page;

import java.util.List;

import javax.inject.Inject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.arcbees.gaestudio.client.debug.DebugIds;
import com.arcbees.gaestudio.selenium.WebDriverHelper;
import com.arcbees.test.ByDebugId;
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
        List<WebElement> checkboxes = booleansContainer.findElements(By.className("checkbox"));

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
        WebElement wheelsContainer = webDriverHelper.waitUntilPresenceOfElementLocated(ByDebugId.id("wheels"));
        WebElement targetWheel = webDriverHelper.findChild(wheelsContainer, ByDebugId.id("0"));
        WebElement textbox = targetWheel.findElement(By.tagName("input"));

        webDriverHelper.typeInTextBox(textbox, "2.0");
    }

    public void clickOnEntityInTable() {
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
        WebElement mixedPropertiesContainer =
                webDriverHelper.waitUntilPresenceOfElementLocated(ByDebugId.id("mixedProperties"));
        WebElement targetproperty = webDriverHelper.findChild(mixedPropertiesContainer, ByDebugId.id("1"));
        WebElement textbox = targetproperty.findElement(By.tagName("input"));

        webDriverHelper.typeInTextBox(textbox, aNewString);
    }

    public void clickDeleteButton() {
        WebElement deleteButton = webDriverHelper.waitUntilElementIsClickable(ByDebugId.id(DebugIds.DELETE_ENGAGE));
        deleteButton.click();
    }

    public void clickDeleteConfirmButton() {
        WebElement deleteConfirmButton = webDriverHelper.waitUntilElementIsClickable(
                ByDebugId.id(DebugIds.DELETE_CONFIRM));
        deleteConfirmButton.click();
    }

    private List<WebElement> getKindWebElements(WebElement allKinds, String kindToFind) {
        return allKinds.findElements(By.xpath("//span[text()='" + kindToFind + "']"));
    }
}
