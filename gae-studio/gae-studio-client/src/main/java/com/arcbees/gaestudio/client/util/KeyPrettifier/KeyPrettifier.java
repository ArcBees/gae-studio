/*
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.util.KeyPrettifier;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.resources.AppMessages;
import com.arcbees.gaestudio.shared.PropertyName;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

public class KeyPrettifier {
    private final AppMessages appMessages;

    @Inject
    KeyPrettifier(AppMessages appMessages) {
        this.appMessages = appMessages;
    }

    public String writeParentKeys(JSONObject propertyValue) {
        String returnValue = "";

        JSONValue parentKeyJson = propertyValue.get(PropertyName.PARENT_KEY);
        JSONObject jsonObject = parentKeyJson.isObject();

        if (jsonObject != null) {
            String kind = jsonObject.get(PropertyName.KIND).isString().stringValue();

            String id = jsonObject.get(PropertyName.ID).toString();
            String name = jsonObject.get(PropertyName.NAME).toString();

            String idName = getIdName(id, name);

            returnValue += writeParentKeys(jsonObject) + appMessages.keyPrettifyTemplate(kind, idName) + appMessages.keyPrettifyChildToken();
        }

        return returnValue;
    }

    public String writeParentKeys(KeyDto key) {
        String returnValue = "";

        if (key == null) {
            return returnValue;
        }

        KeyDto parentKey = key.getParentKey();

        if (parentKey != null) {
            String kind = parentKey.getKind();

            String id = parentKey.getId().toString();
            String name = parentKey.getName();

            String idName = getIdName(id, name);

            returnValue += writeParentKeys(parentKey.getParentKey()) + appMessages.keyPrettifyTemplate(kind, idName) + appMessages.keyPrettifyChildToken();
        }

        return returnValue;
    }

    public String prettifyKey(JSONObject key) {
        String parentValue = writeParentKeys(key);

        String kind = key.get(PropertyName.KIND).isString().stringValue();

        String id = key.get(PropertyName.ID).toString();
        String name = key.get(PropertyName.NAME).toString();
        String idName = getIdName(id, name);

        return parentValue + appMessages.keyPrettifyTemplate(kind, idName);
    }

    public String prettifyKey(KeyDto key) {
        String parentValue = writeParentKeys(key);

        String kind = key.getKind();

        String id = key.getId().toString();
        String name = key.getName();
        String idName = getIdName(id, name);

        return parentValue + appMessages.keyPrettifyTemplate(kind, idName);
    }

    private String getIdName(String id, String name) {
        return "0".equals(id) ? name : id;
    }
}
