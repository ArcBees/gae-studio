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
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException;
import com.github.nmorel.gwtjackson.client.exception.JsonSerializationException;
import com.google.gson.Gson;

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

    private final ColumnVisibilityConfigMapper columnVisibilityConfigMapper = new ColumnVisibilityConfigMapper() {
        @Override
        public Map<String, Map<String, Boolean>> read(String input) throws JsonDeserializationException {
            return new Gson().fromJson(input, Map.class);
        }

        @Override
        public Map<String, Map<String, Boolean>> read(String input, JsonDeserializationContext ctx) throws
                JsonDeserializationException {
            throw new UnsupportedOperationException();
        }

        @Override
        public String write(Map<String, Map<String, Boolean>> value) throws JsonSerializationException {
            return new Gson().toJson(value);
        }

        @Override
        public String write(Map<String, Map<String, Boolean>> value, JsonSerializationContext ctx) throws
                JsonSerializationException {
            throw new UnsupportedOperationException();
        }
    };

    private final ColumnVisibilityConfigHelper helper =
            new ColumnVisibilityConfigHelper(columnVisibilityConfigMapper, storage);

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
        Map<String, Map<String, Boolean>> config = columnVisibilityConfigMapper
                .read(storage.getItem(COLUMN_VISIBILITY_CONFIG));

        assertEquals(DEFAULT_COLUMN_VISIBILITY, config.get("appId$ns$kind").get("col"));
    }
}
