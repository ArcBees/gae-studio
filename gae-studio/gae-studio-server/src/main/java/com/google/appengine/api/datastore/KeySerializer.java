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

import com.arcbees.gaestudio.shared.PropertyName;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * The default serialization of {@link com.google.appengine.api.datastore.Key} does not include the field
 * {@link com.arcbees.gaestudio.shared.dto.entity.KeyDto#encodedKey}.
 *
 * This serializer mimics the default serialization of {@link com.google.appengine.api.datastore.Key}, and adds the
 * {@link com.arcbees.gaestudio.shared.dto.entity.KeyDto#encodedKey} to the returned JsonElement.
 */
public class KeySerializer implements JsonSerializer<Key> {
    @Override
    public JsonElement serialize(Key key, Type typeOfSrc, JsonSerializationContext context) {
        if (key == null) {
            return null;
        }

        JsonObject jsonObject = new JsonObject();

        jsonObject.add(PropertyName.PARENT_KEY, context.serialize(key.getParent()));
        jsonObject.add(PropertyName.KIND, context.serialize(key.getKind()));
        jsonObject.add(PropertyName.APP_ID, context.serialize(key.getAppId()));
        jsonObject.add(PropertyName.ID, context.serialize(key.getId()));
        jsonObject.add(PropertyName.NAME, context.serialize(key.getName()));

        jsonObject.add(PropertyName.APP_ID_NAMESPACE, context.serialize(key.getAppIdNamespace()));

        // this is the field that we add manually using KeySerializer
        jsonObject.add(PropertyName.ENCODED_KEY, context.serialize(getEncodedKey(key)));

        return jsonObject;
    }

    private String getEncodedKey(Key key) {
        if (key.isComplete()) {
            return KeyFactory.keyToString(key);
        } else {
            return null;
        }
    }
}
