/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.api.profiler;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.arcbees.gaestudio.server.channel.ClientId;
import com.arcbees.gaestudio.server.guice.GaeStudioResource;
import com.arcbees.gaestudio.shared.channel.Token;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

@Path(EndPoints.CHANNEL)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@GaeStudioResource
public class ChannelResource {
    private final Provider<String> clientIdProvider;

    @Inject
    ChannelResource(@ClientId Provider<String> clientIdProvider) {
        this.clientIdProvider = clientIdProvider;
    }

    @Path(EndPoints.TOKEN)
    @GET
    public Response createToken() {
        ChannelService channelService = ChannelServiceFactory.getChannelService();
        String tokenValue = channelService.createChannel(clientIdProvider.get());

        Token token = new Token(tokenValue);

        return Response.ok(token).build();
    }
}
