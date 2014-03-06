/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service.mail;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.jukito.TestSingleton;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.arcbees.gaestudio.server.GaeStudioConstants;
import com.arcbees.gaestudio.server.email.ConfirmRegistrationEmailBuilder;
import com.arcbees.gaestudio.server.email.ResetPasswordEmailBuilder;
import com.arcbees.gaestudio.server.velocity.VelocityModule;
import com.arcbees.gaestudio.server.velocity.VelocityWrapper;
import com.arcbees.gaestudio.server.velocity.VelocityWrapperFactory;
import com.arcbees.gaestudio.shared.dto.EmailDto;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.TestEndPoints;
import com.arcbees.gaestudio.testutil.GaeTestBase;
import com.jayway.restassured.http.ContentType;

import static com.jayway.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
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
    ConfirmRegistrationEmailBuilder confirmRegistrationEmailBuilder;
    @Inject
    ResetPasswordEmailBuilder resetPasswordEmailBuilder;
    @Inject
    VelocityWrapperFactory velocityWrapperFactory;
    @Inject
    MessageService messageService;

    private VelocityWrapper velocityWrapper;

    @Test
    public void testGenerateNotificationTemplate() {
        // Given
        String templateLocation =
                "com/arcbees/gaestudio/server/velocitytemplates/messages/mailwrappertemplate.vm";
        velocityWrapper = velocityWrapperFactory.create(templateLocation);

        // when
        velocityWrapper.put("msgDate", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        velocityWrapper.put("title", "Gae Alert");
        velocityWrapper.put("textMessage", "Notification message");
        String notification = velocityWrapper.generate();

        // then
        assertNotNull(notification);
    }

    @Test
    public void sendConfirmtion_shouldReturnNoContent() {
        EmailDto confirmRegistration = messageService.buildConfirmationEmail(GaeStudioConstants.ARCBEES_MAIL_SENDER,
                ANY_TOKEN, GaeStudioConstants.OAUTH_USER_REGISTRATION);

        given().contentType(ContentType.JSON).body(confirmRegistration).header(HttpHeaders.AUTHORIZATION, "apikey")
                .expect().response().statusCode(NO_CONTENT.getStatusCode())
                .when().post(TestEndPoints.ARCBEES_MAIL_SERVICE + EndPoints.MAIL);
    }

    @Test
    public void resetPassword_emailWithPlus_shouldEncode() {
        //when
        String body = resetPasswordEmailBuilder.generateBody(EMAIL_ADDRESS_WITH_PLUS, ANY_TOKEN);

        //then
        assertTrue(body.contains("zom.bee%2Bhello%40arcbees.com"));
    }
}
