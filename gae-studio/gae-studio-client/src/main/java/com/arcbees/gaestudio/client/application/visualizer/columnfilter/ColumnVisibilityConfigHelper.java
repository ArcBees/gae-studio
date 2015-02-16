/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.columnfilter;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.visualizer.columnfilter.storage.StorageAdapter;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;

public class ColumnVisibilityConfigHelper {
    static final boolean DEFAULT_COLUMN_VISIBILITY = true;
    static final String COLUMN_VISIBILITY_CONFIG = "column_visibility_config";

    private final StorageAdapter storage;

    @Inject
    ColumnVisibilityConfigHelper(
            StorageAdapter storage) {
        this.storage = storage;
    }

    public void setColumnVisible(String appId, String namespace, String kind, String columnName, boolean visible) {
        String key = generateKindKey(appId, namespace, kind, columnName);

        storage.setItem(key, Boolean.toString(visible));
    }

    public boolean getColumnVisibility(String appId, String namespace, String kind, String columnName) {
        String kindKey = generateKindKey(appId, namespace, kind, columnName);
        String item = storage.getItem(kindKey);

        boolean visibility;

        if (Strings.isNullOrEmpty(item)) {
            visibility = DEFAULT_COLUMN_VISIBILITY;
            setColumnVisible(appId, namespace, kind, columnName, visibility);
        } else {
            visibility = Boolean.valueOf(item);
        }

        return visibility;
    }

    private String generateKindKey(String appId, String namespace, String kind, String columnName) {
        return Joiner.on("$").join(COLUMN_VISIBILITY_CONFIG, appId, namespace, kind, columnName);
    }
}
