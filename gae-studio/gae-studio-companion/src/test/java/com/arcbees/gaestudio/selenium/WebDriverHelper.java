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

package com.arcbees.gaestudio.selenium;

import java.util.List;

import javax.inject.Inject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.arcbees.gaestudio.server.api.RestIT;
import com.google.common.base.Predicate;

public class WebDriverHelper {
    private final WebDriver webDriver;

    @Inject
    WebDriverHelper(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebDriver webdriver() {
        return webDriver;
    }

    public WebDriverWait webDriverWait() {
        return new WebDriverWait(webDriver, 20);
    }

    public void navigate(String nametoken) {
        webdriver().get(place(nametoken));
    }

    public WebElement waitUntilElementIsClickable(By by) {
        moveToElementLocatedBy(by);

        return webDriverWait().until(ExpectedConditions.elementToBeClickable(by));
    }

    public WebElement findChild(final WebElement parent, final By by) {
        webDriverWait().until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver input) {
                List<WebElement> elements = parent.findElements(by);

                return elements.size() == 1;
            }
        });

        return parent.findElement(by);
    }

    public void typeInTextBox(final WebElement webElement, final String text) {
        waitUntilElementIsClickable(webElement);
        webElement.clear();
        webElement.sendKeys(text);

        webDriverWait().until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver input) {
                String inputText = webElement.getAttribute("value");

                return inputText.equals(text);
            }
        });
    }

    public WebElement waitUntilPresenceOfElementLocated(By locator) {
        return webDriverWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void waitUntilElementIsClickable(final WebElement element) {
        webDriverWait().until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver input) {
                return element.isDisplayed() && element.isEnabled();
            }
        });
    }

    public String getRoot() {
        return RestIT.HOSTNAME + "gae-studio-admin/";
    }

    private String place(String nametoken) {
        return getRoot() + "#" + nametoken;
    }

    private void moveToElementLocatedBy(By by) {
        WebElement webElement = waitUntilPresenceOfElementLocated(by);
        moveToElement(webElement);
    }

    private void moveToElement(WebElement webElement) {
        Actions actions = new Actions(webdriver());
        actions.moveToElement(webElement);
        actions.perform();
    }
}
