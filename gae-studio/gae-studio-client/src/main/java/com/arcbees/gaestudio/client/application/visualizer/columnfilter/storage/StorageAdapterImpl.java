/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.columnfilter.storage;

import com.google.gwt.storage.client.Storage;

public class StorageAdapterImpl implements StorageAdapter {
    private final Storage storage = Storage.getLocalStorageIfSupported();

    @Override
    public void setItem(String key, String data) {
        storage.setItem(key, data);
    }

    @Override
    public String getItem(String key) {
        return storage.getItem(key);
    }
}
