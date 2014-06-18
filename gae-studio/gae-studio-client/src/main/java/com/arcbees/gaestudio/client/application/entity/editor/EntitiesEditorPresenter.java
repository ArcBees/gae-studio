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
import java.util.Set;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.shared.PropertyName;
import com.arcbees.gaestudio.shared.PropertyType;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.assistedinject.Assisted;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;

import static com.arcbees.gaestudio.client.application.entity.editor.EntityEditorPresenter.MyView;

public class EntitiesEditorPresenter extends PresenterWidget<MyView> {
    private final Set<ParsedEntity> parsedEntities;
    private final Map<String, PropertyType> allProperties;
    private final Map<String, JSONValue> commonValues;

    @Inject
    EntitiesEditorPresenter(EventBus eventBus,
                            MyView view,
                            PropertyEditorFactory propertyEditorFactory,
                            @Assisted Set<ParsedEntity> parsedEntities) {
        super(eventBus, view);

        this.parsedEntities = parsedEntities;

        allProperties = Maps.newLinkedHashMap();
        Set<String> propertiesToIgnore = Sets.newHashSet();
        commonValues = Maps.newHashMap();
        Map<String, JSONValue> valuesPrototypes = Maps.newHashMap();
        for (ParsedEntity parsedEntity : parsedEntities) {
            for (String propertyKey : parsedEntity.propertyKeys()) {
                PropertyType propertyType = PropertyUtil.getPropertyType(parsedEntity.getProperty(propertyKey));
                if (!PropertyType.KEY.equals(propertyType)) {
                    allProperties.put(propertyKey, propertyType);
                    JSONValue propertyValue = parsedEntity.getProperty(propertyKey);
                    createPrototypeValue(valuesPrototypes, propertyKey, propertyValue, allProperties.get(propertyKey));

                    if (!propertiesToIgnore.contains(propertyKey) && commonValues.containsKey(propertyKey)) {
                        JSONValue currentValue = commonValues.get(propertyKey);
                        if (!String.valueOf(currentValue).equals(String.valueOf(propertyValue))) {
                            propertiesToIgnore.add(propertyKey);
                            commonValues.remove(propertyKey);
                        }
                    } else if (!propertiesToIgnore.contains(propertyKey)) {
                        commonValues.put(propertyKey, propertyValue);
                    }
                }
            }
        }

        for (Map.Entry<String, PropertyType> propertyEntry : allProperties.entrySet()) {
            String key = propertyEntry.getKey();
            JSONValue propertyValue = getPropertyValue(commonValues, valuesPrototypes, key);

            PropertyEditor<?> propertyEditor
                    = propertyEditorFactory.create(key, propertyEntry.getValue(), propertyValue);
            getView().addPropertyEditor(propertyEditor);
        }
    }

    private JSONValue getPropertyValue(Map<String, JSONValue> commonValues,
                                       Map<String, JSONValue> valuesPrototypes,
                                       String key) {
        JSONValue propertyValue;
        if (commonValues.containsKey(key)) {
            propertyValue = commonValues.get(key);
        } else {
            propertyValue = valuesPrototypes.get(key);
        }

        return propertyValue;
    }

    private void createPrototypeValue(Map<String, JSONValue> valuesPrototypes,
                                      String property,
                                      JSONValue propertyValue,
                                      PropertyType propertyType) {
        if (!valuesPrototypes.containsKey(property)) {
            JSONObject prototype = JSONParser.parseStrict(propertyValue.toString()).isObject();
            prototype.put(PropertyName.VALUE, getDefaultPropertyValue(propertyType));
            valuesPrototypes.put(property, prototype);
        }
    }

    private JSONValue getDefaultPropertyValue(PropertyType propertyType) {
        switch (propertyType) {
            case STRING:
                return new JSONString("");
            default:
                return JSONNull.getInstance();
        }
    }

    public ParsedEntity flush() throws InvalidEntityFieldsException {
        return null;
    }
}
