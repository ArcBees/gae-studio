/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service;

import javax.inject.Singleton;

import com.arcbees.gaestudio.server.service.mail.MessageService;
import com.arcbees.gaestudio.server.service.mail.MessageServiceImpl;
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
        bind(MessageService.class).to(MessageServiceImpl.class).in(Singleton.class);
        bind(GqlService.class).to(GqlServiceImpl.class).in(Singleton.class);
    }
}
