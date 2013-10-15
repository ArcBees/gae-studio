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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.arcbees.oauth.client.domain.Token;
import com.arcbees.oauth.client.domain.User;

public interface AuthService extends RestService {
    @POST
    @Path(EndPoints.REGISTER)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void register(@FormParam(UrlParameters.EMAIL) String email,
                         @FormParam(UrlParameters.PASSWORD) String password,
                         @FormParam(UrlParameters.FIRST_NAME) String firstName,
                         @FormParam(UrlParameters.LAST_NAME) String lastName,
                         MethodCallback<User> callback);

    @POST
    @Path(EndPoints.RESET_PASSWORD)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void generateResetToken(@FormParam(UrlParameters.EMAIL) String email,
                                   MethodCallback<Void> callback);

    @POST
    @Path(EndPoints.LOGIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void login(@FormParam(UrlParameters.EMAIL) String email,
                      @FormParam(UrlParameters.PASSWORD) String password,
                      MethodCallback<Token> callback);
}
