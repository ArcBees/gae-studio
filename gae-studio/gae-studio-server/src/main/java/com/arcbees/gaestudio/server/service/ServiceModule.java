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

package com.arcbees.gaestudio.server.service;

import javax.inject.Singleton;

import com.arcbees.gaestudio.server.service.profiler.OperationService;
import com.arcbees.gaestudio.server.service.profiler.OperationServiceImpl;
import com.arcbees.gaestudio.server.service.profiler.RecordService;
import com.arcbees.gaestudio.server.service.profiler.RecordServiceImpl;
import com.arcbees.gaestudio.server.service.visualizer.BlobsService;
import com.arcbees.gaestudio.server.service.visualizer.BlobsServiceImpl;
import com.arcbees.gaestudio.server.service.visualizer.EntitiesService;
import com.arcbees.gaestudio.server.service.visualizer.EntitiesServiceImpl;
import com.arcbees.gaestudio.server.service.visualizer.EntityService;
import com.arcbees.gaestudio.server.service.visualizer.EntityServiceImpl;
import com.arcbees.gaestudio.server.service.visualizer.ExportService;
import com.arcbees.gaestudio.server.service.visualizer.ExportServiceImpl;
import com.arcbees.gaestudio.server.service.visualizer.GqlService;
import com.arcbees.gaestudio.server.service.visualizer.GqlServiceImpl;
import com.arcbees.gaestudio.server.service.visualizer.ImportService;
import com.arcbees.gaestudio.server.service.visualizer.ImportServiceImpl;
import com.arcbees.gaestudio.server.service.visualizer.KindsService;
import com.arcbees.gaestudio.server.service.visualizer.KindsServiceImpl;
import com.arcbees.gaestudio.server.service.visualizer.NamespacesService;
import com.arcbees.gaestudio.server.service.visualizer.NamespacesServiceImpl;
import com.google.inject.AbstractModule;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(BlobsService.class).to(BlobsServiceImpl.class).in(Singleton.class);
        bind(EntitiesService.class).to(EntitiesServiceImpl.class).in(Singleton.class);
        bind(EntityService.class).to(EntityServiceImpl.class).in(Singleton.class);
        bind(KindsService.class).to(KindsServiceImpl.class).in(Singleton.class);
        bind(NamespacesService.class).to(NamespacesServiceImpl.class).in(Singleton.class);
        bind(OperationService.class).to(OperationServiceImpl.class).in(Singleton.class);
        bind(RecordService.class).to(RecordServiceImpl.class).in(Singleton.class);
        bind(ImportService.class).to(ImportServiceImpl.class).in(Singleton.class);
        bind(ExportService.class).to(ExportServiceImpl.class).in(Singleton.class);
        bind(GqlService.class).to(GqlServiceImpl.class).in(Singleton.class);
    }
}
