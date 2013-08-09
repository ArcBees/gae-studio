/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.rest;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.arcbees.gaestudio.server.GaConstants;
import com.arcbees.gaestudio.server.util.AppEngineHelper;
import com.arcbees.gaestudio.server.util.DatastoreHelper;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.arcbees.gaestudio.shared.dto.entity.ParentKeyDto;
import com.arcbees.gaestudio.server.dto.mapper.EntityMapper;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.GsonDatastoreFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.inject.assistedinject.Assisted;

public class EntityResource extends GoogleAnalyticResource {
    private static final String GET_ENTITY_DTO = "Get Entity Dto";
    private static final String UPDATE_ENTITY = "Update Entity";
    private static final String DELETE_ENTITY = "Delete Entity";

    private final DatastoreHelper datastoreHelper;
    private final Logger logger;
    private final Long entityId;

    @Inject
    EntityResource(DatastoreHelper datastoreHelper,
                   Logger logger,
                   @Assisted Long entityId) {
        this.datastoreHelper = datastoreHelper;
        this.logger = logger;
        this.entityId = entityId;
    }

    @GET
    public EntityDto getEntity(@QueryParam(UrlParameters.NAMESPACE) String namespace,
                               @QueryParam(UrlParameters.APPID) String appId,
                               @QueryParam(UrlParameters.KIND) String kind,
                               @QueryParam(UrlParameters.PARENT_ID) Long parentId,
                               @QueryParam(UrlParameters.PARENT_KIND) String parentKind)
            throws EntityNotFoundException {
        googleAnalytic.trackEvent(GaConstants.CAT_SERVER_CALL, GET_ENTITY_DTO);

        ParentKeyDto parentKeyDto = null;
        if (parentId != null && !Strings.isNullOrEmpty(parentKind)) {
            parentKeyDto = new ParentKeyDto(parentKind, parentId);
        }

        KeyDto keyDto = new KeyDto(kind, entityId, parentKeyDto, new AppIdNamespaceDto(appId, namespace));

        Entity entity = datastoreHelper.get(keyDto);

        return EntityMapper.mapDTO(entity);
    }

    @PUT
    public EntityDto updateEntity(EntityDto entityDto) {
        googleAnalytic.trackEvent(GaConstants.CAT_SERVER_CALL, UPDATE_ENTITY);

        AppEngineHelper.disableApiHooks();
        Entity dbEntity;

        Gson gson = GsonDatastoreFactory.create();
        dbEntity = gson.fromJson(entityDto.getJson(), Entity.class);
        dbEntity.getKey();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(dbEntity);
        logger.info("Entity saved");

        return EntityMapper.mapDTO(dbEntity);
    }

    @DELETE
    public Response deleteEntity(KeyDto keyDto) {
        googleAnalytic.trackEvent(GaConstants.CAT_SERVER_CALL, DELETE_ENTITY);

        AppEngineHelper.disableApiHooks();

        AppIdNamespaceDto namespaceDto = keyDto.getAppIdNamespace();
        Key key = KeyFactory.createKey(keyDto.getKind(), keyDto.getId());

        datastoreHelper.delete(key, namespaceDto.getNamespace());

        return Response.ok().build();
    }
}
