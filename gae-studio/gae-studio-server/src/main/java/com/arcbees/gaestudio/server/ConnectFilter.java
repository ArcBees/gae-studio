package com.arcbees.gaestudio.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.channel.ChannelPresence;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class ConnectFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
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
            storeClientId(clientId);
        } else {
            removeClientId(clientId);
        }
    }

    private ChannelPresence getChannelPresence(MultiReadHttpServletRequest multiReadRequest) throws IOException {
        ChannelService channelService = ChannelServiceFactory.getChannelService();

        return channelService.parsePresence(multiReadRequest);
    }

    private void removeClientId(String clientId) {
        MemcacheService memcacheService = getMemcacheService();
        List<String> clientIds = getClientIds(memcacheService);

        if (clientIds != null) {
            clientIds.remove(clientId);
            memcacheService.put(GaeStudioConstants.GAESTUDIO_OPERATIONS_CLIENT_IDS, clientIds);
        }
    }

    private MemcacheService getMemcacheService() {
        return MemcacheServiceFactory.getMemcacheService();
    }

    private void storeClientId(String clientId) {
        MemcacheService memcacheService = getMemcacheService();
        List<String> clientIds = getClientIds(memcacheService);

        if (clientIds == null) {
            clientIds = new ArrayList<String>();
        }

        clientIds.add(clientId);

        memcacheService.put(GaeStudioConstants.GAESTUDIO_OPERATIONS_CLIENT_IDS, clientIds);
    }

    private List<String> getClientIds(MemcacheService memcacheService) {
        return (List<String>) memcacheService.get(GaeStudioConstants.GAESTUDIO_OPERATIONS_CLIENT_IDS);
    }
}
