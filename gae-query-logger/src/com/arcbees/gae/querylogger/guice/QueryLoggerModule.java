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

import com.arcbees.gae.querylogger.logger.QueryLogger;
import com.arcbees.gae.querylogger.logger.QueryStatisticsCollector;
import com.arcbees.gae.querylogger.logger.SimpleQueryLogger;
import com.google.appengine.api.datastore.QueryLoggerAspect;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.servlet.RequestScoped;
import org.aspectj.lang.Aspects;

import javax.inject.Named;
import java.util.UUID;

public class QueryLoggerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(QueryLogger.class).to(SimpleQueryLogger.class).in(RequestScoped.class);
        bind(QueryStatisticsCollector.class).in(RequestScoped.class);
        requestInjection(Aspects.aspectOf(QueryLoggerAspect.class));
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
        return UUID.randomUUID().toString();
    }

}
