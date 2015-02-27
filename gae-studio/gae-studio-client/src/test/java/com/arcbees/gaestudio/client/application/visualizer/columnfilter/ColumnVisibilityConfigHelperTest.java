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

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.arcbees.gaestudio.client.application.visualizer.columnfilter.storage.StorageAdapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static com.arcbees.gaestudio.client.application.visualizer.columnfilter.ColumnVisibilityConfigHelper
        .COLUMN_VISIBILITY_CONFIG;
import static com.arcbees.gaestudio.client.application.visualizer.columnfilter.ColumnVisibilityConfigHelper
        .DEFAULT_COLUMN_VISIBILITY;

public class ColumnVisibilityConfigHelperTest {
    private final StorageAdapter storage = new StorageAdapter() {
        private final Map<String, String> store = new HashMap<>();

        @Override
        public void setItem(String key, String data) {
            store.put(key, data);
        }

        @Override
        public String getItem(String key) {
            return store.get(key);
        }
    };

    private final ColumnVisibilityConfigHelper helper = new ColumnVisibilityConfigHelper(storage);

    @Test
    public void setGet_true() {
        helper.setColumnVisible("appId", "ns", "kind", "col", true);

        assertTrue(helper.getColumnVisibility("appId", "ns", "kind", "col"));
    }

    @Test
    public void setGet_false() {
        helper.setColumnVisible("appId", "ns", "kind", "col", false);

        assertFalse(helper.getColumnVisibility("appId", "ns", "kind", "col"));
    }

    @Test
    public void get_unexistingColumn_returnsDefaultVisibilityAndStoresDefaultVisibility() {
        assertEquals(DEFAULT_COLUMN_VISIBILITY, helper.getColumnVisibility("appId", "ns", "kind", "col"));

        // we assert that getting a non-exiting column writes the default value to the store
        String key = COLUMN_VISIBILITY_CONFIG + "$appId$ns$kind$col";
        String item = storage.getItem(key);
        assertEquals(DEFAULT_COLUMN_VISIBILITY, Boolean.valueOf(item));
    }
}
