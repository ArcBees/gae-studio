/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.rest;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import com.arcbees.gaestudio.shared.DeleteEntities;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;

public interface EntitiesService extends RestService {
    @GET
    void getByKind(@QueryParam(UrlParameters.KIND) String kind,
                   @QueryParam(UrlParameters.OFFSET) Integer offset,
                   @QueryParam(UrlParameters.LIMIT) Integer limit,
                   MethodCallback<List<EntityDto>> callback);

    @POST
    void createByKind(String kind,
                      MethodCallback<EntityDto> callback);

    @DELETE
    void deleteAll(@QueryParam(UrlParameters.KIND) String kind,
                   @QueryParam(UrlParameters.NAMESPACE) String namespace,
                   @QueryParam(UrlParameters.TYPE) DeleteEntities deleteEntities,
                   MethodCallback<Void> callback);

    @Path(EndPoints.ID)
    EntityService entityService(@PathParam(UrlParameters.ID) Long id);

    @GET
    @Path(EndPoints.COUNT)
    void getCountByKind(@QueryParam(UrlParameters.KIND) String kind,
                        MethodCallback<Integer> callback);
}
