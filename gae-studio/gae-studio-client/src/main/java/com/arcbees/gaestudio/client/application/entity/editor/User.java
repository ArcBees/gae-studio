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

public class User {
    private static final String EMAIL = "email";
    private static final String AUTH_DOMAIN = "authDomain";
    private static final String USER_ID = "userId";
    private static final String FEDERATED_IDENTITY = "federatedIdentity";

    private String email;
    private String authDomain;
    private String userId;
    private String federatedIdentity;

    public static User fromJsonObject(JSONObject jsonObject) {
        User user = new User();

        user.setEmail(getPropertyAsString(jsonObject, EMAIL));
        user.setAuthDomain(getPropertyAsString(jsonObject, AUTH_DOMAIN));
        user.setUserId(getPropertyAsString(jsonObject, USER_ID));
        user.setFederatedIdentity(getPropertyAsString(jsonObject, FEDERATED_IDENTITY));

        return user;
    }

    public JSONObject asJsonObject() {
        JSONObject object = new JSONObject();
        object.put(EMAIL, new JSONString(getEmail()));
        object.put(AUTH_DOMAIN, new JSONString(getAuthDomain()));
        object.put(USER_ID, new JSONString(getUserId()));
        object.put(FEDERATED_IDENTITY, new JSONString(getFederatedIdentity()));

        return object;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthDomain() {
        return authDomain;
    }

    public void setAuthDomain(String authDomain) {
        this.authDomain = authDomain;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFederatedIdentity() {
        return federatedIdentity;
    }

    public void setFederatedIdentity(String federatedIdentity) {
        this.federatedIdentity = federatedIdentity;
    }
}
