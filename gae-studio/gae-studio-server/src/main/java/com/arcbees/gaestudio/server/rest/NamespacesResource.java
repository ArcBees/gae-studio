/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.arcbees.gaestudio.server.GoogleAnalyticConstants;
import com.arcbees.gaestudio.server.guice.GaeStudioResource;
import com.arcbees.gaestudio.server.util.AppEngineHelper;
import com.arcbees.gaestudio.server.util.DatastoreHelper;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.google.api.client.util.Lists;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entities;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.inject.Inject;

@GaeStudioResource
@Path(EndPoints.NAMESPACES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NamespacesResource extends GoogleAnalyticResource {
    private static final String GET_NAMESPACES = "Get Namespaces";

    private final DatastoreHelper datastoreHelper;

    @Inject
    NamespacesResource(DatastoreHelper datastoreHelper) {
        this.datastoreHelper = datastoreHelper;
    }

    @GET
    public List<AppIdNamespaceDto> getNamespaces() {
        googleAnalytic.trackEvent(GoogleAnalyticConstants.CAT_SERVER_CALL, GET_NAMESPACES);

        AppEngineHelper.disableApiHooks();

        Iterable<Entity> entities = datastoreHelper.getAllNamespaces();

        Iterable<AppIdNamespaceDto> namespaces = FluentIterable.from(entities)
                .transform(new Function<Entity, AppIdNamespaceDto>() {
                    @Override
                    public AppIdNamespaceDto apply(Entity input) {
                        return new AppIdNamespaceDto(input.getAppId(),
                                Entities.getNamespaceFromNamespaceKey(input.getKey()));
                    }
                });

        return Lists.newArrayList(namespaces);
    }
}
