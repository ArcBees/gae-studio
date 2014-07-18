/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.entity.editor;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.util.JsonUtils;
import com.arcbees.gaestudio.shared.PropertyName;
import com.arcbees.gaestudio.shared.PropertyType;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
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
    private final JsonUtils jsonUtils;
    private final Set<ParsedEntity> parsedEntities;
    private final Map<String, PropertyType> allProperties;
    private final Map<String, JSONValue> commonValues;
    private final Map<String, PropertyEditor<?>> propertyEditors;
    private final Map<String, JSONValue> valuesPrototypes;
    private final Set<String> propertiesToIgnore;

    @Inject
    EntitiesEditorPresenter(EventBus eventBus,
                            MyView view,
                            PropertyEditorFactory propertyEditorFactory,
                            JsonUtils jsonUtils,
                            @Assisted Set<ParsedEntity> parsedEntities) {
        super(eventBus, view);

        this.jsonUtils = jsonUtils;
        this.parsedEntities = parsedEntities;

        allProperties = Maps.newLinkedHashMap();
        commonValues = Maps.newHashMap();
        propertyEditors = Maps.newLinkedHashMap();
        valuesPrototypes = Maps.newHashMap();
        propertiesToIgnore = Sets.newHashSet();

        parseProperties(jsonUtils, parsedEntities);

        addEditors(propertyEditorFactory);
    }

    public List<EntityDto> flush() throws InvalidEntityFieldsException {
        for (Map.Entry<String, PropertyEditor<?>> editorEntry : propertyEditors.entrySet()) {
            String propertyKey = editorEntry.getKey();
            JSONValue value = editorEntry.getValue().getJsonValue();

            if (commonValueWasModified(propertyKey, value) || otherPropertyWasModified(propertyKey, value)) {
                for (ParsedEntity entity : parsedEntities) {
                    JSONObject propertyMap = entity.getPropertyMap();
                    propertyMap.put(propertyKey, value);
                    entity.getEntityDto().setJson(entity.getJson());
                }
            }
        }

        return FluentIterable.from(parsedEntities)
                .transform(new Function<ParsedEntity, EntityDto>() {
                    @Override
                    public EntityDto apply(ParsedEntity input) {
                        return input.getEntityDto();
                    }
                }).toList();
    }

    private void parseProperties(JsonUtils jsonUtils, Set<ParsedEntity> parsedEntities) {
        Map<String, Integer> commonValuesCount = Maps.newHashMap();
        for (ParsedEntity parsedEntity : parsedEntities) {
            for (String propertyKey : parsedEntity.propertyKeys()) {
                PropertyType propertyType = PropertyUtil.getPropertyType(parsedEntity.getProperty(propertyKey));
                if (!PropertyType.KEY.equals(propertyType)) {
                    allProperties.put(propertyKey, propertyType);
                    JSONValue propertyValue = parsedEntity.getProperty(propertyKey);

                    createPrototypeValue(propertyKey, propertyValue, allProperties.get(propertyKey));

                    checkIfCommonValue(jsonUtils, propertyKey, propertyValue, commonValuesCount);
                }
            }
        }

        cleanSingleValues(commonValuesCount);
    }

    private void cleanSingleValues(Map<String, Integer> commonValuesCount) {
        for (Map.Entry<String, Integer> commonValue : commonValuesCount.entrySet()) {
            if (commonValue.getValue() == 1) {
                commonValues.remove(commonValue.getKey());
            }
        }
    }

    private void checkIfCommonValue(JsonUtils jsonUtils,
                                    String propertyKey,
                                    JSONValue propertyValue,
                                    Map<String, Integer> commonValuesCount) {
        if (!propertiesToIgnore.contains(propertyKey) && commonValues.containsKey(propertyKey)) {
            JSONValue currentValue = commonValues.get(propertyKey);
            if (!jsonUtils.compareObjects(currentValue, propertyValue)) {
                propertiesToIgnore.add(propertyKey);
                commonValues.remove(propertyKey);
            } else {
                commonValuesCount.put(propertyKey, commonValuesCount.get(propertyKey) + 1);
            }
        } else if (!propertiesToIgnore.contains(propertyKey)) {
            commonValues.put(propertyKey, propertyValue);
            commonValuesCount.put(propertyKey, 1);
        }
    }

    private void addEditors(PropertyEditorFactory propertyEditorFactory) {
        for (Map.Entry<String, PropertyType> propertyEntry : allProperties.entrySet()) {
            addEditor(propertyEditorFactory, valuesPrototypes, propertyEntry);
        }
    }

    private boolean otherPropertyWasModified(String propertyKey, JSONValue value) {
        return !commonValues.containsKey(propertyKey) &&
                !jsonUtils.compareObjects(value, valuesPrototypes.get(propertyKey));
    }

    private boolean commonValueWasModified(String propertyKey, JSONValue value) {
        return commonValues.containsKey(propertyKey)
                && !jsonUtils.compareObjects(value, commonValues.get(propertyKey));
    }

    private void addEditor(PropertyEditorFactory propertyEditorFactory,
                           Map<String, JSONValue> valuesPrototypes,
                           Map.Entry<String, PropertyType> propertyEntry) {
        String key = propertyEntry.getKey();
        JSONValue propertyValue = getPropertyValue(commonValues, valuesPrototypes, key);

        PropertyEditor<?> propertyEditor
                = propertyEditorFactory.create(key, propertyEntry.getValue(), propertyValue);
        getView().addPropertyEditor(propertyEditor);

        propertyEditors.put(key, propertyEditor);
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

    private void createPrototypeValue(String property,
                                      JSONValue propertyValue,
                                      PropertyType propertyType) {
        if (!valuesPrototypes.containsKey(property) || propertyType != null) {
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
}
