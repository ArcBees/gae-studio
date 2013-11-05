/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.rest;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.arcbees.gaestudio.server.dto.mapper.BlobInfoMapper;
import com.arcbees.gaestudio.server.guice.GaeStudioResource;
import com.arcbees.gaestudio.server.service.BlobsService;
import com.arcbees.gaestudio.shared.dto.entity.BlobInfoDto;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.google.appengine.api.blobstore.BlobInfo;

@Path(EndPoints.BLOBS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@GaeStudioResource
public class BlobsResource {
    private final BlobsService blobsService;

    @Inject
    BlobsResource(BlobsService blobsService) {
        this.blobsService = blobsService;
    }

    @GET
    public Response getAllKeys() {
        Iterator<BlobInfo> blobInfos = blobsService.getAllBlobInfos();
        List<BlobInfoDto> blobInfoDtos = BlobInfoMapper.mapBlobInfosToDtos(blobInfos);

        return Response.ok(blobInfoDtos).build();
    }
}
