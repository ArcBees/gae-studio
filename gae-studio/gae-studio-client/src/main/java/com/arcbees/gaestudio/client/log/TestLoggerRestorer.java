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
