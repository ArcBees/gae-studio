/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.arcbees.gaestudio.shared.auth.Token;
import com.arcbees.gaestudio.shared.auth.User;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.gwtplatform.dispatch.rest.shared.RestAction;
import com.gwtplatform.dispatch.rest.shared.RestService;

@Path(EndPoints.AUTH)
public interface AuthService extends RestService {
    @POST
    @Path(EndPoints.REGISTER)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    RestAction<User> register(@FormParam(UrlParameters.EMAIL) String email,
                              @FormParam(UrlParameters.PASSWORD) String password,
                              @FormParam(UrlParameters.FIRST_NAME) String firstName,
                              @FormParam(UrlParameters.LAST_NAME) String lastName);

    @POST
    @Path(EndPoints.RESET_PASSWORD)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    RestAction<Void> generateResetToken(@FormParam(UrlParameters.EMAIL) String email);

    @POST
    @Path(EndPoints.LOGIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    RestAction<Token> login(@FormParam(UrlParameters.EMAIL) String email,
                            @FormParam(UrlParameters.PASSWORD) String password);

    @GET
    @Path(EndPoints.LOGIN)
    RestAction<User> checkLogin();
}
