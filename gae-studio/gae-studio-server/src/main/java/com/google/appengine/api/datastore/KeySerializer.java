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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * The default serialization of {@link com.google.appengine.api.datastore.Key} does not include the field {@link
 * com.arcbees.gaestudio.shared.dto.entity.KeyDto#encodedKey}.
 * <p/>
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
