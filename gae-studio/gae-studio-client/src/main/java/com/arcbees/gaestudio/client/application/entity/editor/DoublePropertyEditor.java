/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.entity.editor;

import javax.inject.Inject;

import com.arcbees.gaestudio.shared.PropertyType;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.client.application.entity.editor.PropertyUtil.parseJsonValueWithMetadata;

public class DoublePropertyEditor extends AbstractPropertyEditor<Double> {
    private final DoubleBox doubleBox;
    private final JSONValue property;

    @Inject
    DoublePropertyEditor(@Assisted String key,
                         @Assisted JSONValue property) {
        super(key);

        this.property = property;
        doubleBox = new DoubleBox();

        initFormWidget(doubleBox);
        setValue(PropertyUtil.getPropertyValue(property).isNumber().doubleValue());
    }

    @Override
    public JSONValue getJsonValue() {
        JSONValue value = new JSONNumber(getValue());
        return parseJsonValueWithMetadata(value, PropertyType.FLOATING, PropertyUtil.isPropertyIndexed(property));
    }

    private void setValue(Double value) {
        doubleBox.setValue(value);
    }

    private Double getValue() {
        return doubleBox.getValue();
    }
}
