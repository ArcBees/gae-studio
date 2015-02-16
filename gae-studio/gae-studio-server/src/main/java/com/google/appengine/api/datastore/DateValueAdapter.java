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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateValueAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
    private final SimpleDateFormat dateFormat;

    public DateValueAdapter(String datePattern) {
        this.dateFormat = new SimpleDateFormat(datePattern);
    }

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        synchronized (dateFormat) {
            return new JsonPrimitive(dateFormat.format(src));
        }
    }

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if (!(json instanceof JsonPrimitive)) {
            throw new JsonParseException("The date should be a string value");
        }

        return deserializeToDate(json);
    }

    private Date deserializeToDate(JsonElement json) {
        synchronized (dateFormat) {
            try {
                return dateFormat.parse(json.getAsString());
            } catch (ParseException ignored) {
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return DateValueAdapter.class.getSimpleName() + '(' + dateFormat.getClass().getSimpleName() + ')';
    }
}
