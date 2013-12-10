/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.cucumber.stepdefs;

import javax.inject.Inject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.arcbees.gaestudio.client.debug.DebugIds;
import com.arcbees.gaestudio.client.debug.DebugLogMessages;
import com.arcbees.gaestudio.selenium.ByDebugId;
import com.arcbees.gaestudio.selenium.WebDriverHelper;

public class ProfilerPage {
    private final WebDriverHelper webDriverHelper;

    @Inject
    ProfilerPage(WebDriverHelper webDriverHelper) {
        this.webDriverHelper = webDriverHelper;
    }

    public void clickOnRecord() {
        final WebElement recordButton = webDriverHelper.waitUntilElementIsClickable(ByDebugId.id(DebugIds.RECORD));
        recordButton.click();
    }

    public void assertRequestIsShown() {
        String xpathExpression = "//div[starts-with(text(), 'Request #') and contains(text(), '[1]')]";

        webDriverHelper.waitUntilPresenceOfElementLocated(By.xpath(xpathExpression));
    }

    public void waitForChannelToOpen() {
        String xpathExpression = "//code[contains(text(), " +"'" + DebugLogMessages.CHANNEL_OPENED + "')]";

        webDriverHelper.waitUntilPresenceOfElementLocated(By.xpath(xpathExpression));
    }
}
