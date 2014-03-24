/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.api.mail;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.arcbees.gaestudio.server.GaeStudioConstants;
import com.arcbees.gaestudio.server.guice.GaeStudioResource;
import com.arcbees.gaestudio.server.service.mail.MessageService;
import com.arcbees.gaestudio.shared.dto.EmailDto;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@GaeStudioResource
public class MessageResource {
    private final MessageService messageService;

    @Inject
    MessageResource(MessageService messageService) {
        this.messageService = messageService;
    }

    @POST
    @Path(EndPoints.REGISTRATION + EndPoints.MAIL)
    public Response buildRegistration(@QueryParam(UrlParameters.EMAIL) String email,
                                      @QueryParam(UrlParameters.TOKEN_ID) String tokenId) {
        EmailDto emailToSend = messageService.buildConfirmationEmail(email, tokenId,
                GaeStudioConstants.OAUTH_USER_REGISTRATION);
        ResponseBuilder responseBuilder = Response.ok(emailToSend);

        return responseBuilder.build();
    }

    @POST
    @Path(EndPoints.RESET_PASSWORD + EndPoints.MAIL)
    public Response buildResetPassword(@QueryParam(UrlParameters.EMAIL) String email,
                                       @QueryParam(UrlParameters.TOKEN) String token) {
        EmailDto emailToSend = messageService.buildPasswordEmail(email, token);
        ResponseBuilder responseBuilder = Response.ok(emailToSend);

        return responseBuilder.build();
    }
}
