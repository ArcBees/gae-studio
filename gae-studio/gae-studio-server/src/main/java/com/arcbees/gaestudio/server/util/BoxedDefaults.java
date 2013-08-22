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
        .build();
    }

    @SuppressWarnings("unchecked")
    public static <T> T defaultValue(Class<T> type) {
        return (T) DEFAULTS.get(checkNotNull(type));
    }
}
