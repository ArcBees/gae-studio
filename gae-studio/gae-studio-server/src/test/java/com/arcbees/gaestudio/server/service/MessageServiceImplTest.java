/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service;

import com.arcbees.gaestudio.server.velocity.VelocityWrapper;
import com.arcbees.gaestudio.server.velocity.VelocityWrapperFactory;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(JukitoRunner.class)
public class MessageServiceImplTest {
    @Inject
	VelocityWrapperFactory velocityWrapperFactory;

	private VelocityWrapper velocityWrapper;

    @Test
    public void testGenerate_SendNotification() {
        // Given
		String templateLocation =
				"com/arcbees/gaestudio/server/velocitytemplates/messages/notification.vm";
		velocityWrapper = velocityWrapperFactory.create(templateLocation);

        when(velocityWrapper.put("textMessage", "Gae notification message")).thenReturn(velocityWrapper);

		// when
        String notification = velocityWrapper.generate();

        // then
        // verify(mailService).get(mailReturnedDto);
        assertNull(notification);
    }
}
