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
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.arcbees.gaestudio.client.util.DebugIds;
import com.google.gwt.user.client.ui.UIObject;

public class RootIT {
    private static final String ROOT = "http://localhost:8888/gae-studio-admin/";

    private final WebDriver webDriver = new ChromeDriver();

    @Test
    public void simpleTest() {
        webDriver.get(ROOT);

        assertContainsElementWithId(DebugIds.APPLICATION_ROOT);
    }

    @After
    public void after() {
        webDriver.quit();
    }

    private WebElement assertContainsElementWithId(String id) {
        return webDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.id(UIObject.DEBUG_ID_PREFIX + id)
        ));
    }

    private WebDriverWait webDriverWait() {
        return new WebDriverWait(webDriver, 20);
    }
}
