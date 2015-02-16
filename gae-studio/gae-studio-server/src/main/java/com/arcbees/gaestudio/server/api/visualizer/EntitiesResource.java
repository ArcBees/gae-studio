/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.api.visualizer;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import com.arcbees.gaestudio.server.service.visualizer.EntitiesService;
import com.arcbees.gaestudio.shared.DeleteEntities;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

@Path(EndPoints.ENTITIES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@GaeStudioResource
public class EntitiesResource {
    private final EntitiesService entitiesService;
    private final EntityMapper entityMapper;

    @Inject
    EntitiesResource(EntitiesService entitiesService,
            EntityMapper entityMapper) {
        this.entitiesService = entitiesService;
        this.entityMapper = entityMapper;
    }

    @GET
    public Response getEntities(@QueryParam(UrlParameters.KIND) String kind,
            @QueryParam(UrlParameters.NAMESPACE) String namespace,
            @QueryParam(UrlParameters.OFFSET) Integer offset,
            @QueryParam(UrlParameters.LIMIT) Integer limit) {
        ResponseBuilder responseBuilder;

        if (kind == null) {
            responseBuilder = Response.status(Status.BAD_REQUEST);
        } else {
            Iterable<Entity> entities = entitiesService.getEntities(kind, namespace, offset, limit);
            List<EntityDto> entitiesDtos = entityMapper.mapEntitiesToDtos(entities);

            responseBuilder = Response.ok(entitiesDtos);
        }

        return responseBuilder.build();
    }

    // TODO: Be able to generate entity base schema from the pojo that haven't been saved yet to the datastore
    // We will need to create an implementation to support Objectify, Twig persist, etc.
    // For objectify we can use : ObjectifyService.factory().getMetadataForEntity(String kind);
    // And call method metadata.toEntity
    @POST
    public Response createEmptyEntity(@QueryParam(UrlParameters.KIND) String kind) {
        ResponseBuilder responseBuilder;

        if (Strings.isNullOrEmpty(kind)) {
            responseBuilder = Response.status(Status.BAD_REQUEST);
        } else {
            Entity emptyEntity = entitiesService.createEmptyEntity(kind);
            if (emptyEntity == null) {
                responseBuilder = Response.status(Status.NOT_FOUND);
            } else {
                EntityDto emptyEntityDto = entityMapper.mapEntityToDto(emptyEntity);
                responseBuilder = Response.ok(emptyEntityDto);
            }
        }

        return responseBuilder.build();
    }

    @DELETE
    public Response deleteEntities(@QueryParam(UrlParameters.KIND) String kind,
            @QueryParam(UrlParameters.NAMESPACE) String namespace,
            @QueryParam(UrlParameters.TYPE) DeleteEntities deleteType,
            @QueryParam(UrlParameters.KEY) String encodedKeys) {
        ResponseBuilder responseBuilder;

        if (isValidDeleteRequest(kind, namespace, deleteType, encodedKeys)) {
            entitiesService.deleteEntities(kind, namespace, deleteType, encodedKeys);
            responseBuilder = Response.noContent();
        } else {
            responseBuilder = Response.status(Status.BAD_REQUEST);
        }

        return responseBuilder.build();
    }

    @GET
    @Path(EndPoints.COUNT)
    public Response getCount(@QueryParam(UrlParameters.KIND) String kind,
            @QueryParam(UrlParameters.NAMESPACE) String namespace) {
        ResponseBuilder responseBuilder;

        if (Strings.isNullOrEmpty(kind)) {
            responseBuilder = Response.status(Status.BAD_REQUEST);
        } else {
            long count = entitiesService.getCount(kind, namespace);
            responseBuilder = Response.ok(count);
        }

        return responseBuilder.build();
    }

    @PUT
    public Response updateEntities(List<EntityDto> entitiesDto) throws EntityNotFoundException {
        Collection<Entity> entities = FluentIterable.from(entitiesDto)
                .transform(new Function<EntityDto, Entity>() {
                    @Override
                    public Entity apply(EntityDto input) {
                        return entityMapper.mapDtoToEntity(input);
                    }
                }).toList();

        List<Key> keys = entitiesService.put(entities);
        entities = entitiesService.getEntities(keys);

        return Response.ok(entityMapper.mapEntitiesToDtos(entities)).build();
    }

    private boolean isValidDeleteRequest(String kind,
            String namespace,
            DeleteEntities deleteType,
            String encodedKeys) {
        boolean isValid = false;

        if (deleteType != null) {
            switch (deleteType) {
                case ALL:
                    isValid = true;
                    break;
                case KIND:
                    isValid = kind != null;
                    break;
                case NAMESPACE:
                    isValid = namespace != null;
                    break;
                case KIND_NAMESPACE:
                    isValid = namespace != null && kind != null;
                    break;
                case SET:
                    isValid = !Strings.isNullOrEmpty(encodedKeys);
                    break;
                default:
                    isValid = false;
                    break;
            }
        }

        return isValid;
    }
}
