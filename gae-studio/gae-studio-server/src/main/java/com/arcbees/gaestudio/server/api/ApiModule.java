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

package com.arcbees.gaestudio.server.api;

import com.arcbees.gaestudio.server.api.profiler.ChannelResource;
import com.arcbees.gaestudio.server.api.profiler.RecordResource;
import com.arcbees.gaestudio.server.api.visualizer.BlobsResource;
import com.arcbees.gaestudio.server.api.visualizer.EntitiesResource;
import com.arcbees.gaestudio.server.api.visualizer.EntityResource;
import com.arcbees.gaestudio.server.api.visualizer.ExportResource;
import com.arcbees.gaestudio.server.api.visualizer.GqlResource;
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
        bind(ChannelResource.class);
        bind(RecordResource.class);
        bind(EntityResource.class);
        bind(ImportResource.class);
        bind(ExportResource.class);
        bind(GqlResource.class);
    }
}
