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

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.arcbees.gaestudio.server.appengine.AppEngineModule;
import com.arcbees.gaestudio.server.util.JsonBlobReader;
import com.arcbees.gaestudio.server.util.JsonBlobReaderFactory;
import com.arcbees.gaestudio.testutil.BlobGenerator;
import com.arcbees.gaestudio.testutil.GaeTestBase;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.stream.JsonReader;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import static org.junit.Assert.assertEquals;

@RunWith(JukitoRunner.class)
public class BlobsServiceImplTest extends GaeTestBase {
    public static class Module extends JukitoModule {
        @Override
        protected void configureTest() {
            install(new AppEngineModule());
            install(new FactoryModuleBuilder()
                    .implement(JsonReader.class, JsonBlobReader.class)
                    .build(JsonBlobReaderFactory.class));

            bind(BlobsService.class).to(BlobsServiceImpl.class);
        }
    }

    @Inject
    BlobsService blobsService;
    @Inject
    BlobGenerator blobGenerator;

    @Test
    public void extractEntitiesFromBlob() throws Exception {
        // given
        BlobKey blobKey = createCarBlob();

        // when
        List<Entity> entities = blobsService.extractEntitiesFromBlob(blobKey);

        // then
        assertEquals(1, entities.size());
    }

    private BlobKey createCarBlob() throws IOException, ServletException {
        return blobGenerator.storeBlobFromFile("/Car.json");
    }
}
