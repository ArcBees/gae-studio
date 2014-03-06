/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.arcbees.gaestudio.shared.dto.EmailDto;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.gwtplatform.dispatch.rest.shared.RestAction;
import com.gwtplatform.dispatch.rest.shared.RestService;

public interface MessageService extends RestService {
    @POST
    @Path(EndPoints.REGISTRATION + EndPoints.MAIL)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    RestAction<EmailDto> sendRegistration(@FormParam(UrlParameters.EMAIL) String email,
                                          @FormParam(UrlParameters.TOKEN_ID) String token);

    @POST
    @Path(EndPoints.RESET_PASSWORD + EndPoints.MAIL)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    RestAction<EmailDto> sendResetPassword(@FormParam(UrlParameters.EMAIL) String email,
                                           @FormParam(UrlParameters.TOKEN) String token);

    @POST
    @Path(EndPoints.ARCBEES_MAIL_SERVICE + EndPoints.MAIL)
    RestAction<Void> sendMail(@HeaderParam(HttpHeaders.AUTHORIZATION) String apiKey,
                              @FormParam(UrlParameters.EMAIL) EmailDto email);
}
