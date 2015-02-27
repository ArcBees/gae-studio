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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ShortBlobValueAdapter implements JsonSerializer<ShortBlob>, JsonDeserializer<ShortBlob> {
    @Override
    public ShortBlob deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        byte[] value = context.deserialize(json, byte[].class);

        return new ShortBlob(value);
    }

    @Override
    public JsonElement serialize(ShortBlob shortBlob, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(shortBlob.getBytes(), byte[].class);
    }
}
