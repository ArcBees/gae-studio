/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.util.KeyPrettifier.KeyDtoMapper;
import com.arcbees.gaestudio.client.util.KeyPrettifier.KeyPrettifier;
import com.arcbees.gaestudio.shared.PropertyName;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;

public class ParsedEntityColumnCreator {
    private static final String IS_NULL = "<null>";

    private static int DEFAULT_COLUMN_COUNT;

    private final AppConstants appConstants;
    private final KeyPrettifier keyPrettifier;
    private final KeyDtoMapper keyDtoMapper;

    @Inject
    ParsedEntityColumnCreator(AppConstants appConstants,
                              KeyPrettifier keyPrettifier,
                              KeyDtoMapper keyDtoMapper) {
        this.appConstants = appConstants;
        this.keyPrettifier = keyPrettifier;
        this.keyDtoMapper = keyDtoMapper;
    }

    public static int getDefaultColumnCount() {
        return DEFAULT_COLUMN_COUNT;
    }

    private static void setDefaultColumnCount(int defaultColumnCount) {
        DEFAULT_COLUMN_COUNT = defaultColumnCount;
    }

    public void addPropertyColumn(CellTable<ParsedEntity> cellTable,
                                  final String propertyName) {
        Column<ParsedEntity, ?> column = new TextColumn<ParsedEntity>() {
            @Override
            public String getValue(ParsedEntity parsedEntity) {
                JSONValue jsonProperty = parsedEntity.getProperty(propertyName);

                String stringValue = "";
                if (jsonProperty != null) {
                    stringValue = parsedEntity.getCleanedUpProperty(propertyName).toString();
                    JSONObject parsedJson = JSONParser.parseStrict(stringValue).isObject();

                    if (parsedJson != null) {
                        KeyDto keyDto = keyDtoMapper.fromJSONObject(parsedJson);
                        stringValue = keyPrettifier.prettifyKey(keyDto);
                    }

                    stringValue = addUnindexedIfNeeded(jsonProperty, stringValue);
                }

                return stringValue;
            }
        };

        cellTable.addColumn(column, propertyName);
    }

    public void initializeTable(CellTable<ParsedEntity> entityTable) {
        TextColumn<ParsedEntity> idColumn = buildIdColumn();
        entityTable.addColumn(idColumn, "Key/Parent");

        TextColumn<ParsedEntity> namespaceColumn = buildNameSpaceColumn();
        entityTable.addColumn(namespaceColumn, "Namespace");

        setDefaultColumnCount(entityTable.getColumnCount());
    }

    private TextColumn<ParsedEntity> buildNameSpaceColumn() {
        return new TextColumn<ParsedEntity>() {
            @Override
            public String getValue(ParsedEntity entityJsonParsed) {
                KeyDto keyDto = entityJsonParsed.getKey();
                AppIdNamespaceDto appIdNamespaceDto = keyDto.getAppIdNamespace();
                String namespace = appIdNamespaceDto.getNamespace();
                if (namespace == null) {
                    namespace = IS_NULL;
                } else if (namespace.isEmpty()) {
                    namespace = "";
                }
                return namespace;
            }
        };
    }

    private TextColumn<ParsedEntity> buildIdColumn() {
        return new TextColumn<ParsedEntity>() {
            @Override
            public String getValue(ParsedEntity entityJsonParsed) {
                return keyPrettifier.prettifyKey(entityJsonParsed.getKey());
            }
        };
    }

    private String addUnindexedIfNeeded(JSONValue value, String stringValue) {
        JSONObject jsonObject = value.isObject();

        if (jsonObject != null) {
            JSONValue indexedProperty = jsonObject.get(PropertyName.INDEXED);

            if (indexedProperty != null) {
                boolean isEntityIndexed = indexedProperty.isBoolean().booleanValue();

                if (!isEntityIndexed) {
                    stringValue += " " + appConstants.unindexed();
                }
            }
        }

        return stringValue;
    }
}
