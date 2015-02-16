/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.columnfilter.storage;

/**
 * This interface makes unit testing of {@link com.arcbees.gaestudio.client.application.visualizer.columnfilter
 * .ColumnVisibilityConfigHelper}
 * possible, because {@link com.google.gwt.storage.client.Storage} is a final class and can't be mocked.
 */
public interface StorageAdapter {
    void setItem(String key, String data);

    String getItem(String key);
}
