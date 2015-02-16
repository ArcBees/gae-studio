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

package com.arcbees.gaestudio.server;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.arcbees.gaestudio.server.channel.ClientService;
import com.google.appengine.api.channel.ChannelPresence;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

public class ConnectFilter implements Filter {
    private final ClientService clientService;

    @Inject
    ConnectFilter(
            ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        MultiReadHttpServletRequest multiReadRequest = new MultiReadHttpServletRequest((HttpServletRequest) request);

        manageClientPresence(multiReadRequest);

        chain.doFilter(multiReadRequest, response);
    }

    @Override
    public void destroy() {
    }

    private void manageClientPresence(MultiReadHttpServletRequest multiReadRequest) throws IOException {
        ChannelPresence presence = getChannelPresence(multiReadRequest);

        String clientId = presence.clientId();

        if (presence.isConnected()) {
            clientService.storeClient(clientId);
        } else {
            clientService.removeClient(clientId);
        }
    }

    private ChannelPresence getChannelPresence(MultiReadHttpServletRequest multiReadRequest) throws IOException {
        ChannelService channelService = ChannelServiceFactory.getChannelService();

        return channelService.parsePresence(multiReadRequest);
    }
}
