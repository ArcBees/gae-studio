/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.gwtplatform.dispatch.rest.shared.RestAction;

@Path(EndPoints.GQL)
public interface GqlService {
    @GET
    RestAction<List<EntityDto>> executeGqlRequest(
            @QueryParam(UrlParameters.QUERY) String request,
            @QueryParam(UrlParameters.OFFSET) Integer offset,
            @QueryParam(UrlParameters.LIMIT) Integer limit);

    @Path(EndPoints.COUNT)
    @GET
    RestAction<Integer> getRequestCount(
            @QueryParam(UrlParameters.QUERY) String gqlRequest);
}
