/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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
        StringBuilder sb = new StringBuilder();
        sb.append(DateValueAdapter.class.getSimpleName());
        sb.append('(').append(dateFormat.getClass().getSimpleName()).append(')');
        return sb.toString();
    }
}
