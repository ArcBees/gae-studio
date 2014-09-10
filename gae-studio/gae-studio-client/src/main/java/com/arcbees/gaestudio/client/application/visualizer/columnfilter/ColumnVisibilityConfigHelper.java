/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.columnfilter;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.visualizer.columnfilter.storage.StorageAdapter;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;

public class ColumnVisibilityConfigHelper {
    static final boolean DEFAULT_COLUMN_VISIBILITY = true;
    static final String COLUMN_VISIBILITY_CONFIG = "column_visibility_config";

    private final StorageAdapter storage;
    private final ColumnVisibilityConfigMapper columnVisibilityConfigMapper;

    @Inject
    ColumnVisibilityConfigHelper(ColumnVisibilityConfigMapper columnVisibilityConfigMapper,
                                 StorageAdapter storage) {
        this.storage = storage;
        this.columnVisibilityConfigMapper = columnVisibilityConfigMapper;
    }

    public void setColumnVisible(String appId, String namespace, String kind, String columnName, boolean visible) {
        Map<String, Map<String, Boolean>> visibilityConfig = getVisibilityConfig();

        String kindKey = generateKindKey(appId, namespace, kind);
        Map<String, Boolean> configForKind = visibilityConfig.get(kindKey);

        if (configForKind == null) {
            configForKind = new HashMap<>();
            visibilityConfig.put(kindKey, configForKind);
        }

        configForKind.put(columnName, visible);

        saveVisibilityConfig(visibilityConfig);
    }

    public boolean getColumnVisibility(String appId, String namespace, String kind, String columnName) {
        Map<String, Map<String, Boolean>> visibilityConfig = getVisibilityConfig();

        String kindKey = generateKindKey(appId, namespace, kind);
        Map<String, Boolean> configForKind = visibilityConfig.get(kindKey);

        if (configForKind == null) {
            configForKind = new HashMap<>();
            visibilityConfig.put(kindKey, configForKind);
        }

        if (!configForKind.containsKey(columnName)) {
            configForKind.put(columnName, DEFAULT_COLUMN_VISIBILITY);
            saveVisibilityConfig(visibilityConfig);
        }

        return configForKind.get(columnName);
    }

    private String generateKindKey(String appId, String namespace, String kind) {
        return Joiner.on("$").join(appId, namespace, kind);
    }

    private void saveVisibilityConfig(Map<String, Map<String, Boolean>> visibilityConfig) {
        storage.setItem(COLUMN_VISIBILITY_CONFIG, columnVisibilityConfigMapper.write(visibilityConfig));
    }

    private Map<String, Map<String, Boolean>> getVisibilityConfig() {
        String item = storage.getItem(COLUMN_VISIBILITY_CONFIG);

        if (Strings.isNullOrEmpty(item)) {
            Map<String, Map<String, Boolean>> map = new HashMap<>();
            item = columnVisibilityConfigMapper.write(map);
            storage.setItem(COLUMN_VISIBILITY_CONFIG, item);
        }

        return columnVisibilityConfigMapper.read(item);
    }
}
