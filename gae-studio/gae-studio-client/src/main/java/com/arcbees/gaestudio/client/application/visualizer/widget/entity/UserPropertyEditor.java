/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

import javax.inject.Inject;

import com.arcbees.gaestudio.shared.PropertyType;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.client.application.visualizer.widget.entity.PropertyUtil.parseJsonValueWithMetadata;

public class UserPropertyEditor extends AbstractPropertyEditor<User> {
    interface Binder extends UiBinder<Widget, UserPropertyEditor> {}

    private static final String EMAIL = "email";
    private static final String AUTH_DOMAIN = "authDomain";
    private static final String USER_ID = "userId";
    private static final String FEDERATED_IDENTITY = "federatedIdentity";

    @UiField
    TextBox email;
    @UiField
    TextBox authDomain;
    @UiField
    TextBox userId;
    @UiField
    TextBox federatedIdentity;

    private final JSONValue property;

    @Inject
    UserPropertyEditor(Binder uiBinder,
                       @Assisted String key,
                       @Assisted JSONValue property) {
        super(key);

        this.property = property;

        initFormWidget(uiBinder.createAndBindUi(this));
        setInitialValue();
    }

    @Override
    public JSONValue getJsonValue() {
        JSONObject object = new JSONObject();
        object.put(EMAIL, new JSONString(getEmail()));
        object.put(AUTH_DOMAIN, new JSONString(getAuthDomain()));
        object.put(USER_ID, new JSONString(getUserId()));
        object.put(FEDERATED_IDENTITY, new JSONString(getFederatedIdentity()));

        return parseJsonValueWithMetadata(object, PropertyType.USER, PropertyUtil.isPropertyIndexed(property));
    }

    @Override
    public void setValue(User user) {
        email.setValue(user.getEmail());
        authDomain.setValue(user.getAuthDomain());
        userId.setValue(user.getUserId());
        federatedIdentity.setValue(user.getFederatedIdentity());
    }

    @Override
    public User getValue() {
        User user = new User();
        user.setEmail(getEmail());
        user.setAuthDomain(getAuthDomain());
        user.setUserId(getUserId());
        user.setFederatedIdentity(getFederatedIdentity());

        return user;
    }

    private String getEmail() {
        return email.getValue();
    }

    private String getAuthDomain() {
        return authDomain.getValue();
    }

    private String getUserId() {
        return userId.getValue();
    }

    private String getFederatedIdentity() {
        return federatedIdentity.getValue();
    }

    private void setInitialValue() {
        User user = new User();
        JSONObject imHandleObject = PropertyUtil.getPropertyValue(property).isObject();

        String email = getStringProperty(imHandleObject, EMAIL);
        String authDomain = getStringProperty(imHandleObject, AUTH_DOMAIN);
        String userId = getStringProperty(imHandleObject, USER_ID);
        String federatedIdentity = getStringProperty(imHandleObject, FEDERATED_IDENTITY);

        user.setEmail(email);
        user.setAuthDomain(authDomain);
        user.setUserId(userId);
        user.setFederatedIdentity(federatedIdentity);

        setValue(user);
    }

    private String getStringProperty(JSONObject object, String propertyName) {
        JSONValue property = object.get(propertyName);
        if (property != null && property.isNull() == null) {
            return property.isString().stringValue();
        }

        return "";
    }
}
