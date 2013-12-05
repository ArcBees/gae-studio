/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.companion.rest;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.google.appengine.api.utils.SystemProperty;
import com.googlecode.objectify.Key;

import static com.arcbees.gaestudio.companion.dao.OfyService.ofy;

@Path(TestEndPoints.CLEAR)
public class ClearResource {
    private static final Logger LOGGER = Logger.getLogger(ClearResource.class.getName());

    @GET
    public Response get() {
        ResponseBuilder responseBuilder;

        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
            responseBuilder = Response.status(Status.FORBIDDEN);
        } else {
            List<Key<Object>> keys = ofy().load().keys().list();

            ofy().delete().entities(keys).now();
            ofy().clear();

            LOGGER.info("Database Cleared");

            responseBuilder = Response.ok();
        }

        return responseBuilder.build();
    }
}
