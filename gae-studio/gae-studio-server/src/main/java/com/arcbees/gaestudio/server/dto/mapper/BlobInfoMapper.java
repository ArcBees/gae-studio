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

package com.arcbees.gaestudio.server.dto.mapper;

import java.util.Iterator;
import java.util.List;

import com.arcbees.gaestudio.shared.dto.entity.BlobInfoDto;
import com.google.appengine.api.blobstore.BlobInfo;
import com.google.common.collect.Lists;

public class BlobInfoMapper {
    public static List<BlobInfoDto> mapBlobInfosToDtos(Iterator<BlobInfo> blobInfos) {
        List<BlobInfoDto> blobInfoDtos = Lists.newArrayList();

        while (blobInfos.hasNext()) {
            blobInfoDtos.add(mapBlobInfoToDto(blobInfos.next()));
        }

        return blobInfoDtos;
    }

    public static BlobInfoDto mapBlobInfoToDto(BlobInfo blobInfo) {
        BlobInfoDto blobInfoDto = new BlobInfoDto();
        blobInfoDto.setKey(blobInfo.getBlobKey().getKeyString());
        blobInfoDto.setName(blobInfo.getFilename());
        blobInfoDto.setContentType(blobInfo.getContentType());

        return blobInfoDto;
    }
}
