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

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
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
    private final Logger logger;

    @Inject
    ClearResource(Logger logger) {
        this.logger = logger;
    }

    @GET
    public Response get() {
        ResponseBuilder responseBuilder;

        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
            responseBuilder = Response.status(Status.FORBIDDEN);
        } else {
            List<Key<Object>> keys = ofy().load().keys().list();

            ofy().delete().entities(keys).now();
            ofy().clear();

            logger.info("Database Cleared");

            responseBuilder = Response.ok();
        }

        return responseBuilder.build();
    }
}
