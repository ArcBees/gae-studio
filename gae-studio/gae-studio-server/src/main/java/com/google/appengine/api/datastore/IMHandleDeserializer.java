/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.google.appengine.api.datastore;

import java.lang.reflect.Type;

import com.arcbees.gaestudio.shared.PropertyName;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class IMHandleDeserializer implements JsonDeserializer<IMHandle> {
    @Override
    public IMHandle deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        // Performing no verification as we assume the json is well-formed.
        // Any exceptions thrown by Gson are expected otherwise

        JsonObject imHandleObject = json.getAsJsonObject();
        String protocol = imHandleObject.getAsJsonPrimitive(PropertyName.IM_PROTOCOL).getAsString();
        String address = imHandleObject.getAsJsonPrimitive(PropertyName.IM_ADDRESS).getAsString();

        return IMHandle.fromDatastoreString(protocol + " " + address);
    }
}
