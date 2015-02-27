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

    PropertyEditorCollectionWidget(
            Map<String, PropertyEditor<?>> propertyEditors,
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
