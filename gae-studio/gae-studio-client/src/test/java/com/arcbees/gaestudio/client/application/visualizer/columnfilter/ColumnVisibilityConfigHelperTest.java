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
