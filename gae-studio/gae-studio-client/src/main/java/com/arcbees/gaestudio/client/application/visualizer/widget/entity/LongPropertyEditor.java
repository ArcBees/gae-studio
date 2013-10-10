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
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.LongBox;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.client.application.visualizer.widget.entity.PropertyUtil.parseJsonValueWithMetadata;

public class LongPropertyEditor extends AbstractPropertyEditor<Long> {
    private final LongBox longBox;
    private final JSONValue property;

    @Inject
    LongPropertyEditor(@Assisted String key,
                       @Assisted JSONValue property) {
        super(key);

        this.property = property;
        longBox = new LongBox();

        initFormWidget(longBox);
        setValue((long) PropertyUtil.getPropertyValue(property).isNumber().doubleValue());
    }

    @Override
    public JSONValue getJsonValue() {
        JSONValue value = new JSONNumber(getValue());
        return parseJsonValueWithMetadata(value, PropertyType.NUMERIC, PropertyUtil.isPropertyIndexed(property));
    }

    @Override
    public void setValue(Long value) {
        longBox.setValue(value);
    }

    @Override
    public Long getValue() {
        return longBox.getValue();
    }
}
