/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.googleanalytic;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.arcbees.gaestudio.shared.AnalyticsTrackingIds;

/**
 * This tests must be run while having an internet connexion as it will try to contact GoogleAnalytic
 */
public class GoogleAnalyticTest {
    private static final String APP_NAME = "GAE-Studio";
    private static final String APP_VERSION = "1.0";
    private static final String AN_EVENT_CATEGORY = "test";
    private static final String AN_EVENT_ACTION = "Track event will be successful";
    private static final String A_LABEL = "test label";

    @Test
    public void trackEvent_willBeSuccessful() {
        // Given
        String uuid = UUID.randomUUID().toString();
        GoogleAnalytic googleAnalytic = GoogleAnalytic.build(uuid, AnalyticsTrackingIds.SERVER_TRACKING_ID,
                APP_NAME, APP_VERSION);

        // When
        boolean success = googleAnalytic.trackEvent(AN_EVENT_CATEGORY, AN_EVENT_ACTION);

        // Then - Note, you still have to see the test tracker on Google Analytics to confirm
        // and it may takes some times before the event values are available. Nonetheless, you can see how many users
        // are connected.
        Assert.assertTrue(success);
    }

    @Test
    public void trackEventWithLabel_willBeSuccessful() {
        // Given
        String uuid = UUID.randomUUID().toString();
        GoogleAnalytic googleAnalytic = GoogleAnalytic.build(uuid, AnalyticsTrackingIds.SERVER_TRACKING_ID,
                APP_NAME, APP_VERSION);

        // When
        boolean success = googleAnalytic.trackEvent(AN_EVENT_CATEGORY, AN_EVENT_ACTION, A_LABEL);

        // Then
        Assert.assertTrue(success);
    }

    @Test
    public void trackEvent_willShowMultipleUsers() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            trackEvent_willBeSuccessful();
        }
    }
}
