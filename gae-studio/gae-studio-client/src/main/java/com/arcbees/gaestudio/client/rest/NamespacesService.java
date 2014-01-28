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

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.gwtplatform.dispatch.rest.shared.RestAction;
import com.gwtplatform.dispatch.rest.shared.RestService;

@Path(EndPoints.NAMESPACES)
public interface NamespacesService extends RestService {
    @GET
    RestAction<List<AppIdNamespaceDto>> getNamespaces();
}
