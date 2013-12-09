/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;

public class ParsedEntityColumnCreator {
    public void addColumn(CellTable<ParsedEntity> cellTable,
                          final String propertyName) {
        Column<ParsedEntity, ?> column = new TextColumn<ParsedEntity>() {
            @Override
            public String getValue(ParsedEntity parsedEntity) {
                JSONValue value = parsedEntity.getProperty(propertyName);

                String stringValue = "";
                if (value != null) {
                    stringValue = parsedEntity.getCleanedUpProperty(propertyName).toString();
                }

                return stringValue;
            }
        };

        cellTable.addColumn(column, propertyName);
    }
}
