/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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
