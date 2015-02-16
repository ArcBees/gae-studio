/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.util;

import com.google.gson.stream.JsonReader;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import static com.google.appengine.api.utils.SystemProperty.Environment;

public class UtilModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new FactoryModuleBuilder()
                .implement(JsonReader.class, JsonBlobReader.class)
                .build(JsonBlobReaderFactory.class));

        if (Environment.Value.Production.equals(Environment.environment.value())) {
            bind(DatastoreCountProvider.class).to(ProductionDatastoreCountProvider.class);
        } else {
            bind(DatastoreCountProvider.class).to(DevServerDatastoreCountProvider.class);
        }
    }
}
