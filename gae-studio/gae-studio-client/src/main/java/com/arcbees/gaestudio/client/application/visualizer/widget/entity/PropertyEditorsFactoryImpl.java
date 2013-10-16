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

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.shared.PropertyType;
import com.google.common.collect.Maps;
import com.google.gwt.json.client.JSONValue;

public class PropertyEditorsFactoryImpl implements PropertyEditorsFactory {
    private final PropertyEditorFactory propertyEditorFactory;

    @Inject
    PropertyEditorsFactoryImpl(PropertyEditorFactory propertyEditorFactory) {
        this.propertyEditorFactory = propertyEditorFactory;
    }

    @Override
    public Map<String, PropertyEditor<?>> create(ParsedEntity entity) {
        Map<String, PropertyEditor<?>> propertyEditors = Maps.newHashMap();

        for (String key : entity.getPropertyMap().keySet()) {
            JSONValue property = entity.getProperty(key);
            PropertyType propertyType = PropertyUtil.getPropertyType(property);
            PropertyEditor<?> propertyEditor = createPropertyEditor(key, propertyType, property);

            propertyEditors.put(key, propertyEditor);
        }

        return propertyEditors;
    }

    private PropertyEditor<?> createPropertyEditor(String key, PropertyType propertyType, JSONValue property) {
        PropertyEditor<?> propertyEditor;
        switch (propertyType) {
            case STRING:
                propertyEditor = propertyEditorFactory.createStringEditor(key, property);
                break;
            case NUMERIC:
                propertyEditor = propertyEditorFactory.createNumericEditor(key, property);
                break;
            case FLOATING:
                propertyEditor = propertyEditorFactory.createFloatingEditor(key, property);
                break;
            case DATE:
                propertyEditor = propertyEditorFactory.createDateEditor(key, property);
                break;
            case BOOLEAN:
                propertyEditor = propertyEditorFactory.createBooleanEditor(key, property);
                break;
            default:
                propertyEditor = propertyEditorFactory.createRawEditor(key, property);
        }

        return propertyEditor;
    }
}
