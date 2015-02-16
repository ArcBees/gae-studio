/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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
