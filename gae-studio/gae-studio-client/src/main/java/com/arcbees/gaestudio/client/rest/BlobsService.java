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

import com.arcbees.gaestudio.shared.dto.entity.BlobInfoDto;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.gwtplatform.dispatch.rest.shared.RestAction;

@Path(EndPoints.BLOBS)
public interface BlobsService {
    @GET
    RestAction<List<BlobInfoDto>> getAllKeys();
}
