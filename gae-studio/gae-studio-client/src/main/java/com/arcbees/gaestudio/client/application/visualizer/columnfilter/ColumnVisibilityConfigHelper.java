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
