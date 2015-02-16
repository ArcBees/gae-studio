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

package com.arcbees.gaestudio.client.rest;

import java.util.List;
import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.arcbees.gaestudio.shared.DeleteEntities;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.gwtplatform.dispatch.rest.shared.RestAction;

@Path(EndPoints.ENTITIES)
public interface EntitiesService {
    @GET
    RestAction<List<EntityDto>> getByKind(
            @QueryParam(UrlParameters.KIND) String kind,
            @QueryParam(UrlParameters.NAMESPACE) String currentNamespace,
            @QueryParam(UrlParameters.OFFSET) Integer offset,
            @QueryParam(UrlParameters.LIMIT) Integer limit);

    @GET
    RestAction<List<EntityDto>> getByKind(
            @QueryParam(UrlParameters.KIND) String kind,
            @QueryParam(UrlParameters.OFFSET) Integer offset,
            @QueryParam(UrlParameters.LIMIT) Integer limit);

    @POST
    RestAction<EntityDto> createByKind(
            @QueryParam(UrlParameters.KIND) String kind);

    @DELETE
    RestAction<Void> deleteAll(
            @QueryParam(UrlParameters.KIND) String kind,
            @QueryParam(UrlParameters.NAMESPACE) String namespace,
            @QueryParam(UrlParameters.TYPE) DeleteEntities deleteEntities,
            @QueryParam(UrlParameters.KEY) Set<String> encodedKeys);

    @GET
    @Path(EndPoints.COUNT)
    RestAction<Integer> getCountByKind(
            @QueryParam(UrlParameters.KIND) String kind,
            @QueryParam(UrlParameters.NAMESPACE) String currentNamespace);

    @GET
    @Path(EndPoints.COUNT)
    RestAction<Integer> getCountByKind(
            @QueryParam(UrlParameters.KIND) String kind);

    @PUT
    RestAction<List<EntityDto>> updateEntities(List<EntityDto> entities);
}
