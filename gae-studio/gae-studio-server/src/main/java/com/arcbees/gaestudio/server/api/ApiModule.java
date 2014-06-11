/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.api;

import com.arcbees.gaestudio.server.api.auth.AuthResource;
import com.arcbees.gaestudio.server.api.profiler.OperationsResource;
import com.arcbees.gaestudio.server.api.profiler.RecordResource;
import com.arcbees.gaestudio.server.api.visualizer.BlobsResource;
import com.arcbees.gaestudio.server.api.visualizer.EntitiesResource;
import com.arcbees.gaestudio.server.api.visualizer.EntityResource;
import com.arcbees.gaestudio.server.api.visualizer.ExportCsvResource;
import com.arcbees.gaestudio.server.api.visualizer.ExportJsonResource;
import com.arcbees.gaestudio.server.api.visualizer.ImportResource;
import com.arcbees.gaestudio.server.api.visualizer.KindsResource;
import com.arcbees.gaestudio.server.api.visualizer.NamespacesResource;
import com.google.inject.AbstractModule;

public class ApiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(BlobsResource.class);
        bind(NamespacesResource.class);
        bind(KindsResource.class);
        bind(EntitiesResource.class);
        bind(OperationsResource.class);
        bind(RecordResource.class);
        bind(AuthResource.class);
        bind(EntityResource.class);
        bind(ExportJsonResource.class);
        bind(ImportResource.class);
        bind(ExportCsvResource.class);
    }
}
