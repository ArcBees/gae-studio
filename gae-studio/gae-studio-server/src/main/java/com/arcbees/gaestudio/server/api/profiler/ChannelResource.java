/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
    ChannelResource(
            @ClientId Provider<String> clientIdProvider) {
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
