/*
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.util.KeyPrettifier;

import com.arcbees.gaestudio.shared.PropertyName;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

public class KeyPrettifier {
    public String writeParentKeys(JSONObject propertyValue) {
        String returnValue = "";

        JSONValue parentKeyJson = propertyValue.get(PropertyName.PARENT_KEY);
        JSONObject jsonObject = parentKeyJson.isObject();

        if (jsonObject != null) {
            String kind = jsonObject.get(PropertyName.KIND).isString().stringValue();

            String id = jsonObject.get(PropertyName.ID).toString();
            String name = jsonObject.get(PropertyName.NAME).toString();

            String idName = getIdName(id, name);

            returnValue += writeParentKeys(jsonObject) + kind + " (" + idName + ") > ";
        }

        return returnValue;
    }

    public String prettifyKey(JSONObject key) {
        String parentValue = writeParentKeys(key);

        String kind = key.get(PropertyName.KIND).isString().stringValue();

        String id = key.get(PropertyName.ID).toString();
        String name = key.get(PropertyName.NAME).toString();
        String idName = getIdName(id, name);

        return parentValue + kind + " (" + idName + ")";
    }

    private String getIdName(String id, String name) {
        return id.equals("0") ? name : id;
    }
}
