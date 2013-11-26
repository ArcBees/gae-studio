/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.selenium;

import org.junit.Test;

import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.util.DebugIds;

public class VisualizerIT extends SeleniumTestBase {
    @Test
    public void testNavigateToVisualizer() {
        navigate(NameTokens.visualizer);

        assertContainsElementWithDebugId(DebugIds.VISUALIZER);
    }
}
