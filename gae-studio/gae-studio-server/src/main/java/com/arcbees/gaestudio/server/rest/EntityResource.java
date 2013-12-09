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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.arcbees.gaestudio.server.dto.mapper.EntityMapper;
import com.arcbees.gaestudio.server.guice.GaeStudioResource;
import com.arcbees.gaestudio.server.service.EntityService;
import com.arcbees.gaestudio.server.util.AppEngineHelper;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Path(EndPoints.ENTITY)
@Produces(MediaType.APPLICATION_JSON)
@GaeStudioResource
public class EntityResource {
    private final EntityMapper entityMapper;
    private final EntityService entityService;

    @Inject
    EntityResource(EntityService entityService,
                   EntityMapper entityMapper) {
        this.entityMapper = entityMapper;
        this.entityService = entityService;
    }

    @GET
    public Response getEntity(@QueryParam(UrlParameters.NAMESPACE) String namespace,
                              @QueryParam(UrlParameters.APPID) String appId,
                              @QueryParam(UrlParameters.KIND) String kind,
                              @QueryParam(UrlParameters.PARENT_ID) String parentId,
                              @QueryParam(UrlParameters.PARENT_KIND) String parentKind,
                              @QueryParam(UrlParameters.NAME) String entityName,
                              @QueryParam(UrlParameters.ID) Long entityId)
            throws EntityNotFoundException {
        ResponseBuilder responseBuilder;

        if (isBadRequest(kind, entityName, entityId)) {
            responseBuilder = Response.status(Status.BAD_REQUEST);
        } else {
            Entity entity;

            if (entityId != null) {
                entity = entityService.getEntity(entityId, namespace, appId, kind, parentId, parentKind);
            } else {
                entity = entityService.getEntity(entityName, namespace, appId, kind, parentId, parentKind);
            }

            responseBuilder = buildResponseFromEntity(entity);
        }

        return responseBuilder.build();
    }

    @PUT
    public Response updateEntity(EntityDto newEntityDto) throws EntityNotFoundException {
        ResponseBuilder responseBuilder;
        Entity newEntity = entityMapper.mapDtoToEntity(newEntityDto);
        Entity updatedEntity = entityService.updateEntity(newEntity);

        if (updatedEntity == null) {
            responseBuilder = Response.status(Status.NOT_FOUND);
        } else {
            EntityDto updatedEntityDto = entityMapper.mapEntityToDto(updatedEntity);
            responseBuilder = Response.ok(updatedEntityDto);
        }

        return responseBuilder.build();
    }

    @DELETE
    public Response deleteEntity(@QueryParam(UrlParameters.KIND) String kind,
                                 @QueryParam(UrlParameters.NAME) String entityName,
                                 @QueryParam(UrlParameters.ID) Long entityId) {
        AppEngineHelper.disableApiHooks();
        ResponseBuilder responseBuilder = Response.noContent();
        Key key;

        if (isBadRequest(kind, entityName, entityId)) {
            responseBuilder = Response.status(Status.BAD_REQUEST);
        } else {
            if (entityId != null) {
                key = KeyFactory.createKey(kind, entityId);
            } else {
                key = KeyFactory.createKey(kind, entityName);
            }

            entityService.deleteEntity(key);
        }

        return responseBuilder.build();
    }

    private ResponseBuilder buildResponseFromEntity(Entity entity) {
        ResponseBuilder responseBuilder;

        if (entity == null) {
            responseBuilder = Response.status(Status.NOT_FOUND);
        } else {
            EntityDto entityDto = entityMapper.mapEntityToDto(entity);
            responseBuilder = Response.ok(entityDto);
        }

        return responseBuilder;
    }

    private boolean isBadRequest(String kind, String entityName, Long entityId) {
        return kind == null || nameParameterIsPresent(entityName) && idParameterIsPresent(entityId);
    }

    private boolean nameParameterIsPresent(String entityName) {
        return !Strings.isNullOrEmpty(entityName);
    }

    private boolean idParameterIsPresent(Long entityId) {
        return entityId != null && entityId != 0;
    }
}
