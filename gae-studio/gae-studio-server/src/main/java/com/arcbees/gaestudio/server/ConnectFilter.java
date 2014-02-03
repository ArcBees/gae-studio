/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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
    ConnectFilter(ClientService clientService) {
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
