/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.ui;

import com.google.inject.assistedinject.Assisted;

public interface UiFactory {
    ToolbarButton createToolbarButton(@Assisted("text") String text, @Assisted("iconClass") String iconClass,
                                      final ToolbarButtonCallback callback);

    ToolbarButton createToolbarButton(@Assisted("text") String text, @Assisted("iconClass") String iconClass,
                                      final ToolbarButtonCallback callback, @Assisted("debugId") String debugId);
}
