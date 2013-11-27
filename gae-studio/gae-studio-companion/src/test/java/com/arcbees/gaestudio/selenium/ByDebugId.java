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

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import com.google.gwt.user.client.ui.UIObject;

public class ByDebugId extends By {
    private final String debugId;

    public static By id(String debugId) {
        return new ByDebugId(debugId);
    }

    public ByDebugId(String debugId) {
        this.debugId = debugId;
    }

    @Override
    public WebElement findElement(SearchContext context) {
        return context.findElement(By.id(UIObject.DEBUG_ID_PREFIX + debugId));
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
        return context.findElements(By.id(UIObject.DEBUG_ID_PREFIX + debugId));
    }

    @Override
    public String toString() {
        return debugId;
    }
}
