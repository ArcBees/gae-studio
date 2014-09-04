/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.columnfilter;

import com.google.inject.assistedinject.Assisted;

public interface ColumnNamePresenterFactory {
    String APP_ID = "appId";
    String KIND = "kind";
    String NAMESPACE = "namespace";
    String COLUMN_NAME = "columnName";

    ColumnNamePresenter create(@Assisted(APP_ID) String appId,
                               @Assisted(NAMESPACE) String namespace,
                               @Assisted(KIND) String kind,
                               @Assisted(COLUMN_NAME) String columnName);
}
