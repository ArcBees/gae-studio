/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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

        return Response.ok(stringIdEntity.getName()).build();
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
