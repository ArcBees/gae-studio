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
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.client.application.visualizer.widget.entity.PropertyUtil.parseJsonValueWithMetadata;

public class BooleanPropertyEditor extends AbstractPropertyEditor<Boolean> {
    private final CheckBox checkBox;
    private final JSONValue property;

    @Inject
    BooleanPropertyEditor(@Assisted String key,
                          @Assisted JSONValue property) {
        super(key);

        this.property = property;
        checkBox = new CheckBox();

        initFormWidget(checkBox);
        setValue(PropertyUtil.getPropertyValue(property).isBoolean().booleanValue());
    }

    @Override
    public JSONValue getJsonValue() {
        JSONValue value = JSONBoolean.getInstance(getValue());
        return parseJsonValueWithMetadata(value, PropertyType.BOOLEAN, PropertyUtil.isPropertyIndexed(property));
    }

    private void setValue(Boolean value) {
        checkBox.setValue(value);
    }

    private Boolean getValue() {
        return checkBox.getValue();
    }
}
