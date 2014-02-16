/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service;

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
