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

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.arcbees.gaestudio.server.dto.mapper.EntityMapper;
import com.arcbees.gaestudio.server.guice.GaeStudioResource;
import com.arcbees.gaestudio.server.service.EntitiesService;
import com.arcbees.gaestudio.shared.DeleteEntities;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.google.appengine.api.datastore.Entity;

@Path(EndPoints.ENTITIES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@GaeStudioResource
public class EntitiesResource {
    private final SubresourceFactory subresourceFactory;
    private final EntitiesService entitiesService;

    @Inject
    EntitiesResource(SubresourceFactory subresourceFactory,
                     EntitiesService entitiesService) {
        this.subresourceFactory = subresourceFactory;
        this.entitiesService = entitiesService;
    }

    @GET
    public Response getEntities(@QueryParam(UrlParameters.KIND) String kind,
                                @QueryParam(UrlParameters.OFFSET) Integer offset,
                                @QueryParam(UrlParameters.LIMIT) Integer limit) {
        ResponseBuilder responseBuilder;

        if (kind == null) {
            responseBuilder = Response.status(Status.BAD_REQUEST);
        } else {
            Iterable<Entity> entities = entitiesService.getEntities(kind, offset, limit);

            if (entities == null) {
                responseBuilder = Response.status(Status.NOT_FOUND);
            } else {
                List<EntityDto> entitiesDtos = EntityMapper.mapEntitiesToDtos(entities);
                responseBuilder = Response.ok(entitiesDtos);
            }
        }

        return responseBuilder.build();
    }

    // TODO: Be able to generate entity base schema from the pojo that haven't been saved yet to the datastore
    // We will need to create an implementation to support Objectify, Twig persist, etc.
    // For objectify we can use : ObjectifyService.factory().getMetadataForEntity(String kind);
    // And call method metadata.toEntity

    @POST
    public Response createEmptyEntity(@QueryParam(UrlParameters.KIND) String kind)
            throws InstantiationException, IllegalAccessException {
        ResponseBuilder responseBuilder;

        if (kind == null) {
            responseBuilder = Response.status(Status.BAD_REQUEST);
        } else {
            Entity emptyEntity = entitiesService.createEmptyEntity(kind);
            if (emptyEntity == null) {
                responseBuilder = Response.status(Status.NOT_FOUND);
            } else {
                EntityDto emptyEntityDto = EntityMapper.mapEntityToDto(emptyEntity);
                responseBuilder = Response.ok(emptyEntityDto);
            }
        }

        return responseBuilder.build();
    }

    @DELETE
    public Response deleteEntities(@QueryParam(UrlParameters.KIND) String kind,
                                   @QueryParam(UrlParameters.NAMESPACE) String namespace,
                                   @QueryParam(UrlParameters.TYPE) DeleteEntities deleteType) {
        ResponseBuilder responseBuilder = validateParameters(kind, namespace, deleteType);

        if (responseBuilder.equals(Response.noContent())) {
            entitiesService.deleteEntities(kind, namespace, deleteType);
        }

        return responseBuilder.build();
    }

    private ResponseBuilder validateParameters(String kind, String namespace, DeleteEntities deleteType) {
        ResponseBuilder responseBuilder;

        switch (deleteType) {
            case KIND:
                responseBuilder = kind != null ? Response.noContent() : Response.status(Status.BAD_REQUEST);
                break;
            case NAMESPACE:
                responseBuilder = namespace != null ? Response.noContent() : Response.status(Status.BAD_REQUEST);
                break;
            case KIND_NAMESPACE:
                responseBuilder = namespace != null && kind != null ? Response.noContent() : Response.status(Status.BAD_REQUEST);
                break;
            case ALL:
                responseBuilder = Response.noContent();
                break;
            default:
                responseBuilder = Response.status(Status.BAD_REQUEST);
                break;
        }

        return responseBuilder;
    }

    @GET
    @Path(EndPoints.COUNT)
    public Response getCount(@QueryParam(UrlParameters.KIND) String kind) {
        ResponseBuilder responseBuilder;

        if (kind == null) {
            responseBuilder = Response.status(Status.BAD_REQUEST);
        } else {
            Integer count = entitiesService.getCount(kind);
            responseBuilder = Response.ok(count);
        }

        return responseBuilder.build();
    }

    @Path(EndPoints.ID)
    public EntityResource getEntityResource(@PathParam(UrlParameters.ID) Long id) {
        return subresourceFactory.createEntityResource(id);
    }
}
