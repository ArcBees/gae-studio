/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.entity.editor;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

import static com.arcbees.gaestudio.client.application.entity.editor.PropertyUtil.getPropertyAsString;
import static com.arcbees.gaestudio.shared.PropertyName.IM_ADDRESS;
import static com.arcbees.gaestudio.shared.PropertyName.IM_PROTOCOL;

public class IMHandle {
    private String protocol;
    private String address;

    public IMHandle(String protocol, String address) {
        this.protocol = protocol;
        this.address = address;
    }

    public static IMHandle fromJsonObject(JSONObject jsonObject) {
        String protocol = getPropertyAsString(jsonObject, IM_PROTOCOL);
        String address = getPropertyAsString(jsonObject, IM_ADDRESS);

        return new IMHandle(protocol, address);
    }

    public JSONObject asJsonObject() {
        JSONObject object = new JSONObject();
        object.put(IM_PROTOCOL, new JSONString(getProtocol()));
        object.put(IM_ADDRESS, new JSONString(getAddress()));

        return object;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
