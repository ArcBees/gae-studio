/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.util;

import com.google.gson.JsonElement;

import static com.arcbees.gaestudio.shared.PropertyName.VALUE;

public final class JsonUtil {
    private JsonUtil() {
    }

    public static boolean hasEmbedValue(JsonElement json) {
        return json.isJsonObject() && json.getAsJsonObject().has(VALUE);
    }
}
