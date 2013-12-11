/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.rest;

import javax.inject.Singleton;

import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;

import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;

public class RestModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(ResourceFactory.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    EntitiesService getEntitiesService(ResourceFactory resourceFactory) {
        return resourceFactory.setupProxy(GWT.<EntitiesService>create(EntitiesService.class), EndPoints.ENTITIES);
    }

    @Provides
    @Singleton
    EntityService getEntityService(ResourceFactory resourceFactory) {
        return resourceFactory.setupProxy(GWT.<EntityService>create(EntityService.class), EndPoints.ENTITY);
    }

    @Provides
    @Singleton
    NamespacesService getNamespacesService(ResourceFactory resourceFactory) {
        return resourceFactory.setupProxy(GWT.<NamespacesService>create(NamespacesService.class), EndPoints.NAMESPACES);
    }

    @Provides
    @Singleton
    KindsService getKindsService(ResourceFactory resourceFactory) {
        return resourceFactory.setupProxy(GWT.<KindsService>create(KindsService.class), EndPoints.KINDS);
    }

    @Provides
    @Singleton
    OperationsService getOperationsService(ResourceFactory resourceFactory) {
        return resourceFactory.setupProxy(GWT.<OperationsService>create(OperationsService.class), EndPoints.OPERATIONS);
    }

    @Provides
    @Singleton
    RecordService getRecordService(ResourceFactory resourceFactory) {
        return resourceFactory.setupProxy(GWT.<RecordService>create(RecordService.class), EndPoints.RECORD);
    }

    @Provides
    @Singleton
    AuthService getAuthService(ResourceFactory resourceFactory) {
        return resourceFactory.setupProxy(GWT.<AuthService>create(AuthService.class), EndPoints.AUTH);
    }

    @Provides
    @Singleton
    BlobsService getBlobsService(ResourceFactory resourceFactory) {
        return resourceFactory.setupProxy(GWT.<BlobsService>create(BlobsService.class), EndPoints.BLOBS);
    }

    @Provides
    @Singleton
    LicenseService getLicenseService() {
        Resource resource = new Resource(EndPoints.ARCBEES_LICENSE_SERVICE);
        LicenseService proxy = GWT.create(LicenseService.class);
        ((RestServiceProxy) proxy).setResource(resource);

        return proxy;
    }
}
