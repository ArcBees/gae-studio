/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.arcbees.gaestudio.server.GaConstants;
import com.arcbees.gaestudio.server.guice.GaeStudioResource;
import com.arcbees.gaestudio.server.recorder.HookRegistrar;
import com.arcbees.gaestudio.server.recorder.MemcacheKey;
import com.arcbees.gaestudio.server.recorder.authentication.Listener;
import com.arcbees.gaestudio.server.recorder.authentication.ListenerProvider;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.googleanalytic.GoogleAnalytic;
import com.google.appengine.api.memcache.MemcacheService;

@GaeStudioResource
@Path(EndPoints.RECORD)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecordResource extends GoogleAnalyticResource {
    private static final String SET_RECORDING = "Set Recording";

    private final HookRegistrar hookRegistrar;
    private final ListenerProvider listenerProvider;
    private final GoogleAnalytic googleAnalytic;
    private final MemcacheService memcacheService;

    @Inject
    RecordResource(HookRegistrar hookRegistrar,
                   ListenerProvider listenerProvider,
                   GoogleAnalytic googleAnalytic,
                   MemcacheService memcacheService) {
        this.hookRegistrar = hookRegistrar;
        this.listenerProvider = listenerProvider;
        this.googleAnalytic = googleAnalytic;
        this.memcacheService = memcacheService;
    }

    @POST
    public Long startRecording() {
        googleAnalytic.trackEvent(GaConstants.CAT_SERVER_CALL, SET_RECORDING);

        Listener listener = listenerProvider.get();

        hookRegistrar.putListener(listener);

        return getMostRecentId();
    }

    @DELETE
    public Long stopRecording() {
        googleAnalytic.trackEvent(GaConstants.CAT_SERVER_CALL, SET_RECORDING);

        Listener listener = listenerProvider.get();

        hookRegistrar.removeListener(listener);

        return getMostRecentId();
    }

    private Long getMostRecentId() {
        Long mostRecentId = (Long) memcacheService.get(MemcacheKey.DB_OPERATION_COUNTER.getName());
        if (mostRecentId == null) {
            mostRecentId = 0L;
        }
        return mostRecentId;
    }
}
