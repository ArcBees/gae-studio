/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.server.recorder;

public enum MemcacheKey {
    DB_OPERATION_COUNTER("db.operation.counter"),
    DB_OPERATION_RECORD_PREFIX("db.operation.record.");

    private String name;

    MemcacheKey(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
