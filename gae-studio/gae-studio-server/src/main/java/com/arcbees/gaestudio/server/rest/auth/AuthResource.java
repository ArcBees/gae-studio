/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.rest.auth;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.arcbees.oauth.client.OAuthClient;
import com.arcbees.oauth.client.UserClient;
import com.arcbees.oauth.client.domain.Token;
import com.arcbees.oauth.client.domain.User;

@Path(EndPoints.AUTH)
public class AuthResource {
    public static final String API_TOKEN = "ljhs98234h24o8dsyfjehrljqh01923874j2hj";

    private final OAuthClient oAuthClient;
    private final UserClient userClient;

    @Inject
    AuthResource(OAuthClient oAuthClient,
                 UserClient userClient) {
        this.oAuthClient = oAuthClient;
        this.userClient = userClient;
    }

    @Path(EndPoints.REGISTER)
    @POST
    public Response register(@FormParam(UrlParameters.EMAIL) String email,
                             @FormParam(UrlParameters.PASSWORD) String password,
                             @FormParam(UrlParameters.FIRST_NAME) String firstName,
                             @FormParam(UrlParameters.LAST_NAME) String lastName) {

        Token bearerToken = getBearerToken();

        User user = userClient.register(bearerToken, email, password, firstName, lastName);

        return Response.ok(user).build();
    }

    @POST
    @Path(EndPoints.RESET_PASSWORD)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response generateResetToken(@FormParam(UrlParameters.EMAIL) String email) {
        Token bearerToken = getBearerToken();

        userClient.requestResetPassword(bearerToken, email);

        return Response.ok().build();
    }

    @POST
    @Path(EndPoints.LOGIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam(UrlParameters.EMAIL) String email,
                          @FormParam(UrlParameters.PASSWORD) String password) {
        Token authToken = oAuthClient.login(email, password);

        return Response.ok(authToken).build();
    }

    private Token getBearerToken() {
        return oAuthClient.getBearerToken(API_TOKEN);
    }
}
