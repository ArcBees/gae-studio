/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.api.auth;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.arcbees.gaestudio.server.guice.GaeStudioResource;
import com.arcbees.gaestudio.server.service.auth.AuthService;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.arcbees.oauth.api.domain.User;

@Path(EndPoints.AUTH)
@GaeStudioResource
public class AuthResource {
    private final AuthService authService;

    @Inject
    AuthResource(AuthService authService) {
        this.authService = authService;
    }

    @POST
    @Path(EndPoints.REGISTER)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response register(@FormParam(UrlParameters.EMAIL) String email,
                             @FormParam(UrlParameters.PASSWORD) String password,
                             @FormParam(UrlParameters.FIRST_NAME) String firstName,
                             @FormParam(UrlParameters.LAST_NAME) String lastName) {

        Long userId = authService.register(email, password, firstName, lastName);

        return Response.ok(userId).build();
    }

    @POST
    @Path(EndPoints.RESET_PASSWORD)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response generateResetToken(@FormParam(UrlParameters.EMAIL) String email) {
        authService.requestResetToken(email);

        return Response.ok().build();
    }

    @GET
    @Path(EndPoints.LOGIN)
    public Response checkLogin() {
        User user = authService.checkLogin();

        return Response.ok(user).build();
    }

    @POST
    @Path(EndPoints.LOGIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam(UrlParameters.EMAIL) String email,
                          @FormParam(UrlParameters.PASSWORD) String password) {
        authService.login(email, password);

        return checkLogin();
    }

    @DELETE
    @Path(EndPoints.LOGIN)
    public Response logout() {
        authService.logout();

        return Response.ok().build();
    }
}
