/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.gwtplatform.dispatch.rest.shared.RestAction;
import com.gwtplatform.dispatch.rest.shared.RestService;

@Path(EndPoints.ENTITY)
public interface EntityService extends RestService {
    @GET
    RestAction<EntityDto> getEntity(@QueryParam(UrlParameters.KIND) String kind,
                                    @QueryParam(UrlParameters.APPID) String appId,
                                    @QueryParam(UrlParameters.NAMESPACE) String namespace,
                                    @QueryParam(UrlParameters.PARENT_ID) String parentId,
                                    @QueryParam(UrlParameters.PARENT_KIND) String parentKind,
                                    @QueryParam(UrlParameters.NAME) String entityName,
                                    @QueryParam(UrlParameters.ID) Long entityId);

    @PUT
    RestAction<EntityDto> updateEntity(EntityDto entityDto);

    @DELETE
    RestAction<Void> deleteEntity(@QueryParam(UrlParameters.KIND) String kind,
                                  @QueryParam(UrlParameters.NAME) String entityName,
                                  @QueryParam(UrlParameters.ID) Long entityId);
}
