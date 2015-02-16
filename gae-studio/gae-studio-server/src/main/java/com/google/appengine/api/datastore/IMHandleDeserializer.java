/**
 * Copyright 2015 ArcBees Inc.
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
