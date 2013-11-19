/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.companion.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.arcbees.gaestudio.companion.dao.StringIdEntityDao;
import com.arcbees.gaestudio.companion.domain.StringIdEntity;

@Path(TestEndPoints.STRING_ID_ENTITY)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StringIdEntityResource {
    private final StringIdEntityDao stringIdEntityDao;

    @Inject
    public StringIdEntityResource(StringIdEntityDao stringIdEntityDao) {
        this.stringIdEntityDao = stringIdEntityDao;
    }

    @POST
    public Response createEntity(StringIdEntity stringIdEntity) {
        stringIdEntityDao.put(stringIdEntity);

        return Response.ok(stringIdEntity.getId()).build();
    }

    @GET
    public Response getEntity(@QueryParam(TestEndPoints.PARAM_ID) String id) {
        StringIdEntity stringIdEntity = stringIdEntityDao.get(id);

        return Response.ok(stringIdEntity).build();
    }

    @DELETE
    public Response deleteCar(String id) {
        stringIdEntityDao.delete(id);

        return Response.noContent().build();
    }
}
