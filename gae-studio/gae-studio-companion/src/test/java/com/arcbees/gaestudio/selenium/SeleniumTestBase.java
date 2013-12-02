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

import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.arcbees.gaestudio.server.rest.RestIT;
import com.google.common.base.Predicate;
import com.google.gwt.user.client.ui.UIObject;

public class SeleniumTestBase extends RestIT {
    private final WebDriver webDriver;

    SeleniumTestBase() {
        webDriver = new ChromeDriver();
    }

    public String getRoot() {
        return HOSTNAME + "gae-studio-admin/";
    }

    public WebDriver webdriver() {
        return webDriver;
    }

    public WebDriverWait webDriverWait() {
        return new WebDriverWait(webDriver, 20);
    }

    @After
    public void after() {
        webdriver().quit();
    }

    public WebElement assertContainsElementWithDebugId(String debugId) {
        return webDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.id(UIObject
                .DEBUG_ID_PREFIX + debugId)
        ));
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
