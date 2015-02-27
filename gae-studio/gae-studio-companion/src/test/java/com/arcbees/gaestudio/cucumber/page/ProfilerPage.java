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

import javax.inject.Inject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.arcbees.gaestudio.client.debug.DebugIds;
import com.arcbees.gaestudio.client.debug.DebugLogMessages;
import com.arcbees.gaestudio.selenium.WebDriverHelper;
import com.arcbees.test.ByDebugId;

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
        String xpathExpression = "//code[contains(text(), " + "'" + DebugLogMessages.CHANNEL_OPENED + "')]";

        webDriverHelper.waitUntilPresenceOfElementLocated(By.xpath(xpathExpression));
    }
}
