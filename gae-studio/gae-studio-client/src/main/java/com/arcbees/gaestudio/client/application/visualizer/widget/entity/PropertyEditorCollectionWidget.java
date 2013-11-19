/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class PropertyEditorCollectionWidget implements IsWidget {
    private final Map<String, PropertyEditor<?>> propertyEditors;
    private final JSONObject propertyMap;
    private final FlowPanel panel;

    PropertyEditorCollectionWidget(Map<String, PropertyEditor<?>> propertyEditors,
                                   JSONObject propertyMap) {
        this.propertyEditors = propertyEditors;
        this.propertyMap = propertyMap;
        panel = new FlowPanel();

        for (PropertyEditor<?> propertyEditor : propertyEditors.values()) {
            panel.add(propertyEditor);
        }
    }

    public void flush() throws InvalidEntityFieldsException {
        validateEditors();
        updateProperties();
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    public Map<String, ?> getValue() {
        Map<String, Object> values = Maps.newHashMap();

        for (Entry<String, ?> entry : propertyEditors.entrySet()) {
            values.put(entry.getKey(), entry.getValue());
        }

        return values;
    }

    private void updateProperties() {
        for (Entry<String, PropertyEditor<?>> entry : propertyEditors.entrySet()) {
            String key = entry.getKey();
            PropertyEditor<?> propertyEditor = entry.getValue();

            JSONValue newJsonValue = propertyEditor.getJsonValue();
            propertyMap.put(key, newJsonValue);
        }
    }

    private void validateEditors() {
        boolean valid = true;
        for (Entry<String, PropertyEditor<?>> entry : propertyEditors.entrySet()) {
            PropertyEditor<?> propertyEditor = entry.getValue();

            valid &= propertyEditor.isValid();
        }

        if (!valid) {
            throw new InvalidEntityFieldsException();
        }
    }
}
