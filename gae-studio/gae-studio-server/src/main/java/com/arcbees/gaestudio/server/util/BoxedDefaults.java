/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.util;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import static com.google.common.base.Preconditions.checkNotNull;

public final class BoxedDefaults {
    private BoxedDefaults() {
    }

    private static final Map<Class, Object> DEFAULTS;

    static {
        DEFAULTS = ImmutableMap.<Class, Object>builder()
                .put(Boolean.class, false)
                .put(Character.class, '\0')
                .put(Byte.class, (byte) 0)
                .put(Short.class, (short) 0)
                .put(Integer.class, 0)
                .put(Long.class, 0L)
                .put(Float.class, 0f)
                .put(Double.class, 0d)
                .put(String.class, "")
                .build();
    }

    @SuppressWarnings("unchecked")
    public static <T> T defaultValue(Class<T> type) {
        return (T) DEFAULTS.get(checkNotNull(type));
    }
}
