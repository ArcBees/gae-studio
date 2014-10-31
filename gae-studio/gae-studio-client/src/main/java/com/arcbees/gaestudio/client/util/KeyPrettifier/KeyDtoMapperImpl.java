/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.util.KeyPrettifier;

import javax.inject.Inject;

import com.arcbees.gaestudio.shared.PropertyName;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.google.gwt.json.client.JSONObject;

public class KeyDtoMapperImpl implements KeyDtoMapper {
    private final AppIdNamespaceDtoMapper appIdNamespaceDtoMapper;

    @Inject
    KeyDtoMapperImpl(AppIdNamespaceDtoMapper appIdNamespaceDtoMapper) {
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
