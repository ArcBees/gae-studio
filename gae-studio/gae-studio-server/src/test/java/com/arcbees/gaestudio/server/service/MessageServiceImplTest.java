/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.jukito.TestSingleton;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.arcbees.gaestudio.server.email.ConfirmRegistrationEmailGenerator;
import com.arcbees.gaestudio.server.email.ResetPasswordEmailBodyGenerator;
import com.arcbees.gaestudio.server.velocity.VelocityModule;
import com.arcbees.gaestudio.server.velocity.VelocityWrapper;
import com.arcbees.gaestudio.server.velocity.VelocityWrapperFactory;
import com.arcbees.gaestudio.testutil.GaeTestBase;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(JukitoRunner.class)
public class MessageServiceImplTest extends GaeTestBase {
    public static class Module extends JukitoModule {
        @Override
        protected void configureTest() {
            install(new VelocityModule());
            bind(MessageService.class).to(MessageServiceImpl.class).in(TestSingleton.class);
        }
    }

    private final String EMAIL_ADDRESS_WITH_PLUS = "zom.bee+hello@arcbees.com";
    private final String ANY_TOKEN = "randomToken";

    @Inject
    ConfirmRegistrationEmailGenerator confirmRegistrationEmailGenerator;
    @Inject
    ResetPasswordEmailBodyGenerator resetPasswordEmailBodyGenerator;
    @Inject
    VelocityWrapperFactory velocityWrapperFactory;
    @Inject
    MessageService messageService;

    private VelocityWrapper velocityWrapper;

    @Test
    public void testGenerateNotificationTemplate() {
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

    @Test
    public void confirmRegistration_emailWithPlus_shouldEncode() {
        //when
        String body = confirmRegistrationEmailGenerator.generateBody(EMAIL_ADDRESS_WITH_PLUS, ANY_TOKEN);

        //then
        assertTrue(body.contains("zom.bee%2Bhello%40arcbees.com"));
    }

    @Test
    public void resetPassword_emailWithPlus_shouldEncode() {
        //when
        String body = resetPasswordEmailBodyGenerator.generateBody(EMAIL_ADDRESS_WITH_PLUS, ANY_TOKEN);

        //then
        assertTrue(body.contains("zom.bee%2Bhello%40arcbees.com"));
    }
}
