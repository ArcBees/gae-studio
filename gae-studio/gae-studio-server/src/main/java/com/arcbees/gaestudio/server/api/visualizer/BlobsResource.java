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

package com.arcbees.gaestudio.server.api.visualizer;

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
import com.arcbees.gaestudio.server.service.visualizer.BlobsService;
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
    BlobsResource(
            BlobsService blobsService) {
        this.blobsService = blobsService;
    }

    @GET
    public Response getAllKeys() {
        Iterator<BlobInfo> blobInfos = blobsService.getAllBlobInfos();
        List<BlobInfoDto> blobInfoDtos = BlobInfoMapper.mapBlobInfosToDtos(blobInfos);

        return Response.ok(blobInfoDtos).build();
    }
}
