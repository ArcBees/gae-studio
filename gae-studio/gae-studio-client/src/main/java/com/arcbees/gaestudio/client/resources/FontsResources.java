/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.GssResource;

public interface FontsResources extends ClientBundle {
    public interface Icons extends GssResource {
        String icon_cancel();

        String icon_delete();

        String icon_deselect();

        String icon_dropdown();

        String icon_edit();

        String icon_export();

        String icon_firstpage();

        String icon_gqlclose();

        String icon_gqlopen();

        String icon_import();

        String icon_lastpage();

        String icon_next();

        String icon_previous();

        String icon_refresh();

        String icon_save();

        String icon_submit();

        String icon_menu();
    }

    @Source("fonts/icons.ttf")
    DataResource iconsTtf();

    @Source("fonts/icons.eot")
    DataResource iconsEot();

    @Source("fonts/icons.svg")
    DataResource iconsSvg();

    @Source("fonts/icons.woff")
    DataResource iconsWoff();

    @Source("fonts/icons.gss")
    Icons icons();
}
