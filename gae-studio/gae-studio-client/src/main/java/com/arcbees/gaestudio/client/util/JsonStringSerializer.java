/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.util;

import java.io.IOException;

import javax.annotation.Nonnull;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

public class JsonStringSerializer extends JsonSerializer<String> {
    @Override
    protected void doSerialize(JsonWriter writer, @Nonnull String value, JsonSerializationContext ctx,
                               JsonSerializerParameters params) throws IOException {
        writer.rawValue("\"" + value.replaceAll("\"", "\\\\\"") + "\"");
    }
}
