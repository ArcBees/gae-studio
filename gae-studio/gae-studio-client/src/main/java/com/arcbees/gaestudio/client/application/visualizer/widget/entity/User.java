/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

import static com.arcbees.gaestudio.client.application.visualizer.widget.entity.PropertyUtil.getPropertyAsString;

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
