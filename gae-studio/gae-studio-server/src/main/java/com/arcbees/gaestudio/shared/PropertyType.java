/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared;

public enum PropertyType {
    NUMERIC("java.lang.Long"),
    FLOATING("java.lang.Double"),
    BOOLEAN("java.lang.Boolean"),
    STRING("java.lang.String"),
    TEXT("com.google.appengine.api.datastore.Text", STRING),
    DATE("java.util.Date"),
    POSTAL_ADDRESS("com.google.appengine.api.datastore.PostalAddress"),
    CATEGORY("com.google.appengine.api.datastore.Category"),
    LINK("com.google.appengine.api.datastore.Link"),
    EMAIL("com.google.appengine.api.datastore.Email"),
    PHONE_NUMBER("com.google.appengine.api.datastore.PhoneNumber"),
    RATING("com.google.appengine.api.datastore.Rating"),
    GEO_PT("com.google.appengine.api.datastore.GeoPt"),
    EMBEDDED("com.google.appengine.api.datastore.EmbeddedEntity"),
    COLLECTION("java.util.Collection"),
    KEY("com.google.appengine.api.datastore.Key"),
    NULL("java.lang.Object");

    private final String mappedClass;
    private final PropertyType representation;

    PropertyType(String mappedClass) {
        this(mappedClass, null);
    }

    PropertyType(String mappedClass,
                 PropertyType representation) {
        this.mappedClass = mappedClass;
        this.representation = representation == null ? this : representation;
    }

    public String getMappedClass() {
        return mappedClass;
    }

    public PropertyType getRepresentation() {
        return representation;
    }
}
