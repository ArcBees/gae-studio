/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.selenium;

import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gwt.user.client.ui.UIObject;

public class SeleniumTestBase {
    private final WebDriver webDriver;

    SeleniumTestBase() {
        webDriver = new ChromeDriver();
    }

    public String getRoot() {
        return "http://localhost:8888/gae-studio-admin/";
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

    private String place(String nametoken) {
        return getRoot() + "#" + nametoken;
    }
}
