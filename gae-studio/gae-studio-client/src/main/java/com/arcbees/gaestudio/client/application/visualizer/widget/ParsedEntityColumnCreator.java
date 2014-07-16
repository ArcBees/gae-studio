/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.shared.PropertyName;
import com.arcbees.gaestudio.shared.PropertyType;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.arcbees.gaestudio.shared.dto.entity.ParentKeyDto;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;

public class ParsedEntityColumnCreator {
    public static int getDefaultColumnCount() {
        return DEFAULT_COLUMN_COUNT;
    }

    private static void setDefaultColumnCount(int defaultColumnCount) {
        DEFAULT_COLUMN_COUNT = defaultColumnCount;
    }

    private static final String IS_NULL = "<null>";
    private static int DEFAULT_COLUMN_COUNT;

    public void addPropertyColumn(CellTable<ParsedEntity> cellTable,
                                  final String propertyName) {
        Column<ParsedEntity, ?> column = new TextColumn<ParsedEntity>() {
            @Override
            public String getValue(ParsedEntity parsedEntity) {
                JSONValue jsonProperty = parsedEntity.getProperty(propertyName);

                String stringValue = "";
                if (jsonProperty != null) {
                    stringValue = parsedEntity.getCleanedUpProperty(propertyName).toString();
                    stringValue = prettifyKey(jsonProperty, stringValue);
                    stringValue = addUnindexedIfNeeded(jsonProperty, stringValue);
                }

                return stringValue;
            }
        };

        cellTable.addColumn(column, propertyName);
    }

    public void initializeTable(CellTable<ParsedEntity> entityTable) {
        TextColumn<ParsedEntity> idColumn = buildIdColumn();
        entityTable.addColumn(idColumn, "ID/NAME");

        TextColumn<ParsedEntity> parentKindColumn = buildParentKindColumn();
        entityTable.addColumn(parentKindColumn, "Parent Kind");

        TextColumn<ParsedEntity> parentIdColumn = buildParentIdColumn();
        entityTable.addColumn(parentIdColumn, "Parent ID");

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

    private TextColumn<ParsedEntity> buildParentKindColumn() {
        return new TextColumn<ParsedEntity>() {
            @Override
            public String getValue(ParsedEntity entityJsonParsed) {
                ParentKeyDto parentKeyDTO = entityJsonParsed.getKey().getParentKey();
                if (parentKeyDTO == null) {
                    return IS_NULL;
                }
                return parentKeyDTO.getKind();
            }
        };
    }

    private TextColumn<ParsedEntity> buildParentIdColumn() {
        return new TextColumn<ParsedEntity>() {
            @Override
            public String getValue(ParsedEntity entityJsonParsed) {
                ParentKeyDto parentKeyDTO = entityJsonParsed.getKey().getParentKey();
                if (parentKeyDTO == null) {
                    return IS_NULL;
                }
                return parentKeyDTO.getId().toString();
            }
        };
    }

    private TextColumn<ParsedEntity> buildIdColumn() {
        return new TextColumn<ParsedEntity>() {
            @Override
            public String getValue(ParsedEntity entityJsonParsed) {
                String idName;

                if (entityJsonParsed.getKey().getId() != 0) {
                    idName = entityJsonParsed.getKey().getId().toString();
                } else {
                    idName = entityJsonParsed.getKey().getName();
                }

                return idName;
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
                    stringValue += " (unindexed)";
                }
            }
        }

        return stringValue;
    }

    private String prettifyKey(JSONValue jsonProperty, String stringValue) {
        JSONObject jsonObject = jsonProperty.isObject();
        String value = stringValue;

        if (jsonObject != null) {
            JSONValue jsonPropertyType = jsonObject.get(PropertyName.GAE_PROPERTY_TYPE);

            if (jsonPropertyType != null) {
                PropertyType propertyType = PropertyType.valueOf(jsonPropertyType.isString().stringValue());

                if (propertyType == PropertyType.KEY) {
                    JSONObject propertyValue = jsonObject.get(PropertyName.VALUE).isObject();
                    String kind = propertyValue.get(PropertyName.KIND).isString().stringValue();
                    String id = String.valueOf(propertyValue.get(PropertyName.ID));

                    value = kind + " (" + id + ")";
                }
            }
        }

        return value;
    }
}
