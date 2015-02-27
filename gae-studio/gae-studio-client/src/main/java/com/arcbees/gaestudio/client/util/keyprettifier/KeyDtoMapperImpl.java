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

package com.arcbees.gaestudio.client.util.keyprettifier;

import javax.inject.Inject;

import com.arcbees.gaestudio.shared.PropertyName;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.google.gwt.json.client.JSONObject;

public class KeyDtoMapperImpl implements KeyDtoMapper {
    private final AppIdNamespaceDtoMapper appIdNamespaceDtoMapper;

    @Inject
    KeyDtoMapperImpl(
            AppIdNamespaceDtoMapper appIdNamespaceDtoMapper) {
        this.appIdNamespaceDtoMapper = appIdNamespaceDtoMapper;
    }

    @Override
    public KeyDto fromJSONObject(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }

        String encodedKey = jsonObject.get(PropertyName.ENCODED_KEY).isString().stringValue();
        String kind = jsonObject.get(PropertyName.KIND).isString().stringValue();
        Long id = Long.valueOf(jsonObject.get(PropertyName.ID).toString());
        String name = jsonObject.get(PropertyName.NAME).toString();

        JSONObject parentKeyJson = jsonObject.get(PropertyName.PARENT_KEY).isObject();
        KeyDto parentKey = fromJSONObject(parentKeyJson);
        JSONObject appIdNamespaceDtoJSONObject = jsonObject.get(PropertyName.APP_ID_NAMESPACE).isObject();
        AppIdNamespaceDto appIdNamespace = appIdNamespaceDtoMapper.fromJSONObject(appIdNamespaceDtoJSONObject);

        return new KeyDto(encodedKey, kind, id, name, parentKey, appIdNamespace);
    }
}
