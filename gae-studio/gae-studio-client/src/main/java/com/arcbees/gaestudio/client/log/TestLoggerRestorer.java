/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.log;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.logging.client.HasWidgetsLogHandler;
import com.google.gwt.logging.client.LoggingPopup;

public class TestLoggerRestorer implements LoggerRestorer {
    private final Logger logger;

    @Inject
    TestLoggerRestorer(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void restorePopupLogger() {
        LoggingPopup loggingWidget = GWT.create(LoggingPopup.class);
        loggingWidget.getElement().getStyle().setDisplay(Style.Display.NONE);
        logger.addHandler(new HasWidgetsLogHandler(loggingWidget));
    }
}
