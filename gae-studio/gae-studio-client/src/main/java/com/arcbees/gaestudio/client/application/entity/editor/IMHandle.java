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

package com.arcbees.gaestudio.client.application.entity.editor;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

import static com.arcbees.gaestudio.client.application.entity.editor.PropertyUtil.getPropertyAsString;
import static com.arcbees.gaestudio.shared.PropertyName.IM_ADDRESS;
import static com.arcbees.gaestudio.shared.PropertyName.IM_PROTOCOL;

public class IMHandle {
    private String protocol;
    private String address;

    public IMHandle(
            String protocol,
            String address) {
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
