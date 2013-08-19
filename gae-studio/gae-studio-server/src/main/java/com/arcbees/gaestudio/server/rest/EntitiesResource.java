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

import com.arcbees.gaestudio.server.dto.mapper.EntityMapper;
import com.arcbees.gaestudio.server.service.EntitiesService;
import com.arcbees.gaestudio.shared.DeleteEntities;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.google.appengine.api.datastore.Entity;

@Path(EndPoints.ENTITIES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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
    public List<EntityDto> getEntities(@QueryParam(UrlParameters.KIND) String kind,
                                       @QueryParam(UrlParameters.OFFSET) Integer offset,
                                       @QueryParam(UrlParameters.LIMIT) Integer limit) {
        Iterable<Entity> entities = entitiesService.getEntities(kind, offset, limit);

        return EntityMapper.mapEntitiesToDtos(entities);
    }

    @POST
    public EntityDto createEmptyEntity(@QueryParam(UrlParameters.KIND) String kind) {
        Entity emptyEntity = null;

        try {
            emptyEntity = entitiesService.createEmptyEntity(kind);
        } catch (Exception e) {
            // TODO: Be able to generate entity base schema from the pojo that haven't been saved yet to the datastore
            // We will need to create an implementation to support Objectify, Twig persist, etc.
            // For objectify we can use : ObjectifyService.factory().getMetadataForEntity(String kind);
            // And call method metadata.toEntity
        }

        return EntityMapper.mapEntityToDto(emptyEntity);
    }

    @DELETE
    public void deleteEntities(@QueryParam(UrlParameters.KIND) String kind,
                               @QueryParam(UrlParameters.NAMESPACE) String namespace,
                               @QueryParam(UrlParameters.TYPE) DeleteEntities deleteType) {
        entitiesService.deleteEntities(kind, namespace, deleteType);
    }

    @GET
    @Path(EndPoints.COUNT)
    public Integer getCount(@QueryParam(UrlParameters.KIND) String kind) {
        return entitiesService.getCount(kind);
    }

    @Path(EndPoints.ID)
    public EntityResource getEntityResource(@PathParam(UrlParameters.ID) Long id) {
        return subresourceFactory.createEntityResource(id);
    }
}
