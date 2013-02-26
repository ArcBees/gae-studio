/**
 * Copyright 2013 ArcBees Inc.
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

package com.arcbees.gaestudio.server.guice;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO externalize magic strings
public class RequestIdProvider implements Provider<Long> {
    private final MemcacheService memcacheService;

    @Inject
    public RequestIdProvider(final MemcacheService memcacheService) {
        this.memcacheService = memcacheService;
    }

    @Override
    public Long get() {
        return memcacheService.increment("request.counter", 1L, 0L);
    }
}
