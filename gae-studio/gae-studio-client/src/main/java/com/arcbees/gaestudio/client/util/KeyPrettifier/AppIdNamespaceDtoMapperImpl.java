/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.util.KeyPrettifier;

import com.arcbees.gaestudio.shared.PropertyName;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.google.gwt.json.client.JSONObject;

public class AppIdNamespaceDtoMapperImpl implements AppIdNamespaceDtoMapper {
    @Override
    public AppIdNamespaceDto fromJSONObject(JSONObject jsonObject) {
        String appId = jsonObject.get(PropertyName.APP_ID).isString().toString();
        String namespace = jsonObject.get(PropertyName.NAMESPACE).isString().toString();

        return new AppIdNamespaceDto(appId, namespace);
    }
}
