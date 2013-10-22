/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.ui;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.inject.assistedinject.Assisted;

public class PropertyColumn extends TextColumn<ParsedEntity> {
    private String property;

    @Inject
    PropertyColumn(@Assisted String property) {
        this.property = property;
    }

    @Override
    public String getValue(ParsedEntity parsedEntity) {
        if (parsedEntity.hasProperty(property)) {
            JSONValue value = parsedEntity.getCleanedUpProperty(property);
            if (value == null) {
                return "<null>";
            } else if (value.isObject() != null) {
                JSONObject object = value.isObject();
                if (object.containsKey("kind") && object.containsKey("id")) {
                    return object.get("kind").isString().stringValue() + ", " + object.get("id");
                }
            } else if (value.isString() != null) {
                return value.isString().stringValue();
            }
            return value.toString();
        } else {
            return "<missing>";
        }
    }
}
