/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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
