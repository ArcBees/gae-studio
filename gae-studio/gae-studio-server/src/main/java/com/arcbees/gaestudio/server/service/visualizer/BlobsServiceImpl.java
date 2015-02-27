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
    BlobsServiceImpl(
            BlobInfoFactory blobInfoFactory,
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

        return gson.fromJson(jsonReader, new TypeToken<List<Entity>>() {
        }.getType());
    }
}
