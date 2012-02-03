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
