package com.arcbees.gaestudio.server.rest;

public interface SubresourceFactory {
    EntityResource createEntityResource(Long entityId);
}
