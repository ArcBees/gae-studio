package com.arcbees.googleanalytic;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

/**
 * This tests must be run while having an internet connexion as it will try to contact GoogleAnalytic
 */
public class GoogleAnalyticTest {
    @Test
    public void trackEvent_willBeSuccessful() {
        // Given
        String uuid = UUID.randomUUID().toString();

        GoogleAnalytic googleAnalytic = GoogleAnalytic.build(uuid, "UA-41550930-5", "GAE-Studio", "1.0");

        // When
        boolean success = googleAnalytic.trackEvent("test", "Track event will be successful");

        // Then - Note, you still have to see the test tracker on Google Analytics to confirm
        // and it may takes some times before the event values are available. Nonetheless, you can see how many users
        // are connected.
        Assert.assertTrue(success);
    }

    @Test
    public void trackEvent_willShowMultipleUsers() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            trackEvent_willBeSuccessful();
        }
    }
}
