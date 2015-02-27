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

import javax.inject.Inject;

import com.arcbees.gaestudio.shared.PropertyType;
import com.google.common.collect.Maps;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

public class PropertyEditorCollectionWidgetFactoryImpl implements PropertyEditorCollectionWidgetFactory {
    private final PropertyEditorFactory propertyEditorFactory;

    @Inject
    PropertyEditorCollectionWidgetFactoryImpl(
            PropertyEditorFactory propertyEditorFactory) {
        this.propertyEditorFactory = propertyEditorFactory;
    }

    @Override
    public PropertyEditorCollectionWidget create(JSONObject propertyMap) {
        Map<String, PropertyEditor<?>> propertyEditors = Maps.newTreeMap();

        for (String key : propertyMap.keySet()) {
            JSONValue property = propertyMap.get(key);
            PropertyType propertyType = PropertyUtil.getPropertyType(property);
            PropertyEditor<?> propertyEditor = propertyEditorFactory.create(key, propertyType, property);

            propertyEditors.put(key, propertyEditor);
        }

        return new PropertyEditorCollectionWidget(propertyEditors, propertyMap);
    }
}
