/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.gwtplatform.dispatch.rest.shared.RestAction;

@Path(EndPoints.ARCBEES_LICENSE_SERVICE)
public interface LicenseService {
    @GET
    @Path(EndPoints.CHECK)
    RestAction<Void> checkLicense(@QueryParam(UrlParameters.ID) Long userId);

    @POST
    @Path(EndPoints.REGISTER)
    RestAction<Void> register(LicenseRegistration licenseRegistration);
}
