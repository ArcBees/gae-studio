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

package com.arcbees.googleanalytic;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.arcbees.gaestudio.shared.AnalyticsTrackingIds;

/**
 * This tests must be run while having an internet connexion as it will try to contact GoogleAnalytic.
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
