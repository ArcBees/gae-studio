/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.rest;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.arcbees.gaestudio.client.application.support.MessageRequest;
import com.gwtplatform.dispatch.rest.shared.RestAction;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Path("https://mail.arcbees.com/mail")
public interface MailService {
    @POST
    RestAction<Void> sendMessage(MessageRequest messageRequest, @HeaderParam(AUTHORIZATION) String authorization);
}
