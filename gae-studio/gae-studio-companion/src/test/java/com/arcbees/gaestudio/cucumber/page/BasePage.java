/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.cucumber.page;

import javax.inject.Inject;

import org.openqa.selenium.WebElement;

import com.arcbees.gaestudio.client.debug.DebugIds;
import com.arcbees.gaestudio.selenium.WebDriverHelper;
import com.arcbees.test.ByDebugId;

import static org.junit.Assert.assertEquals;

public class BasePage {
    private final WebDriverHelper webDriverHelper;

    @Inject
    BasePage(WebDriverHelper webDriverHelper) {
        this.webDriverHelper = webDriverHelper;
    }

    public void navigate(String nametoken) {
        webDriverHelper.navigate(nametoken);
    }

    public void waitForSaveConfirmation(String message) {
        WebElement element = webDriverHelper.waitUntilPresenceOfElementLocated(ByDebugId.id(DebugIds.MESSAGE));
        assertEquals("The save message is not correct", message, element.getText());
    }
}
