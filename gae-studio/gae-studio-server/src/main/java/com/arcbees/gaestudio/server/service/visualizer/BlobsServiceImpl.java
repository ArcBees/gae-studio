/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service.visualizer;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import com.arcbees.gaestudio.server.util.JsonBlobReaderFactory;
import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class BlobsServiceImpl implements BlobsService {
    private final BlobInfoFactory blobInfoFactory;
    private final JsonBlobReaderFactory jsonBlobReaderFactory;
    private final Gson gson;

    @Inject
    BlobsServiceImpl(BlobInfoFactory blobInfoFactory,
                     JsonBlobReaderFactory jsonBlobReaderFactory,
                     Gson gson) {
        this.blobInfoFactory = blobInfoFactory;
        this.jsonBlobReaderFactory = jsonBlobReaderFactory;
        this.gson = gson;
    }

    @Override
    public Iterator<BlobInfo> getAllBlobInfos() {
        return blobInfoFactory.queryBlobInfos();
    }

    @Override
    public List<Entity> extractEntitiesFromBlob(BlobKey blobKey) {
        JsonReader jsonReader = jsonBlobReaderFactory.create(blobKey);

        return gson.fromJson(jsonReader, new TypeToken<List<Entity>>() {}.getType());
    }
}
