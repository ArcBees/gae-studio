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
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.client.application.visualizer.widget.entity.PropertyUtil.parseJsonValueWithMetadata;

public class BytesPropertyEditor extends AbstractPropertyEditor<String> {
    private final TextBox textBox;
    private final JSONValue property;

    @Inject
    BytesPropertyEditor(@Assisted String key,
                        @Assisted JSONValue property) {
        super(key);

        this.property = property;
        textBox = new TextBox();
        textBox.setEnabled(false);

        initFormWidget(textBox);
        setValue(PropertyUtil.getPropertyValue(property).isArray().toString());
    }

    @Override
    public JSONValue getJsonValue() {
        JSONArray value = JSONParser.parseStrict(getValue()).isArray();
        Boolean isIndexed = PropertyUtil.isPropertyIndexed(property);
        PropertyType propertyType = PropertyUtil.getPropertyType(property);

        return parseJsonValueWithMetadata(value, propertyType, isIndexed);
    }

    @Override
    public void setValue(String value) {
        textBox.setValue(value);
    }

    @Override
    public String getValue() {
        return textBox.getValue();
    }
}
