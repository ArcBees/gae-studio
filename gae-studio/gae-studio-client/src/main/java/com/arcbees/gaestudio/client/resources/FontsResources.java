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

package com.arcbees.gaestudio.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.DataResource;
// CHECKSTYLE_OFF
public interface FontsResources extends ClientBundle {
    public interface Icons extends CssResource {
        String icon_cancel();

        String icon_delete();

        String icon_deselect();

        String icon_dropdown();

        String icon_columns();

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
