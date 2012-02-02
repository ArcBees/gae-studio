/*
 * Copyright 2011 ArcBees Inc.
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

package com.arcbees.gae.querylogger.guice;

import com.arcbees.gae.querylogger.analyzer.QueryAnalyzer;
import com.arcbees.gae.querylogger.recorder.MemcacheQueryRecorder;
import com.arcbees.gae.querylogger.recorder.QueryRecorder;
import com.arcbees.gae.querylogger.recorder.QueryRecorderHook;
import com.arcbees.gae.querylogger.recorder.SimpleStackInspector;
import com.arcbees.gae.querylogger.recorder.StackInspector;
import com.fasterxml.uuid.Generators;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Named;
import com.google.inject.servlet.RequestScoped;

public class QueryLoggerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(StackInspector.class).to(SimpleStackInspector.class);
        // TODO what should be pluggable is the shared persistence interface (memcache)
        bind(QueryRecorder.class).to(MemcacheQueryRecorder.class).in(RequestScoped.class);
        bind(QueryAnalyzer.class).in(RequestScoped.class);
        
        install(new FactoryModuleBuilder()
                .implement(QueryRecorderHook.class, QueryRecorderHook.class)
                .build(QueryRecorderHookFactory.class));
    }

    @Provides
    @RequestScoped
    private MemcacheService memcacheServiceProvider() {
        return MemcacheServiceFactory.getMemcacheService();
    }
    
    @Provides
    @Named("requestId")
    @RequestScoped
    private String requestIdProvider() {
        // We could get significantly fancier here to guarantee UUID uniqueness
        // (see JUG documentation), but this should do for now.
        return Generators.timeBasedGenerator().generate().toString();
    }

}
