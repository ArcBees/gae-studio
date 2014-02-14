/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service;

import com.arcbees.gaestudio.server.velocity.VelocityModule;
import com.arcbees.gaestudio.server.velocity.VelocityWrapper;
import com.arcbees.gaestudio.server.velocity.VelocityWrapperFactory;
import com.arcbees.gaestudio.testutil.GaeTestBase;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertNotNull;

@RunWith(JukitoRunner.class)
public class MessageServiceImplTest extends GaeTestBase {

    public static class Module extends JukitoModule {
        @Override
        protected void configureTest() {
            install(new VelocityModule());
        }
    }

    @Inject
    VelocityWrapperFactory velocityWrapperFactory;

	private VelocityWrapper velocityWrapper;

    @Test
    public void testGenerate_SendNotification() {
        // Given
		String templateLocation =
				"com/arcbees/gaestudio/server/velocitytemplates/messages/notification.vm";
		velocityWrapper = velocityWrapperFactory.create(templateLocation);

		// when
        velocityWrapper.put("msgDate", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        velocityWrapper.put("textMessage", "Gae notification message");
        String notification = velocityWrapper.generate();

        // then
        assertNotNull(notification);
    }
}
