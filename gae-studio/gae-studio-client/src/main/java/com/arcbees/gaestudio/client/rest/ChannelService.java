/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.arcbees.gaestudio.shared.channel.Constants;
import com.arcbees.gaestudio.shared.channel.Token;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.gwtplatform.dispatch.rest.shared.RestAction;
import com.gwtplatform.dispatch.rest.shared.RestService;

@Path(EndPoints.CHANNEL)
public interface ChannelService extends RestService {
    @Path(EndPoints.TOKEN)
    @GET
    RestAction<Token> getToken(@QueryParam(Constants.CLIENT_ID) String clientId);
}
