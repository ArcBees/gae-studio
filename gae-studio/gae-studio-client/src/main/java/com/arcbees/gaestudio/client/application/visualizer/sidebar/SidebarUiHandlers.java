/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.sidebar;

import com.gwtplatform.mvp.client.UiHandlers;

interface SidebarUiHandlers extends UiHandlers {
    void displayEntitiesOfSelectedKind(String kind);

    void onCloseHandleActivated();

    void importKind();

    void exportCurrentKind();

    void exportCsv();

    void importCsv();

    void deleteAllOfKind();
}
