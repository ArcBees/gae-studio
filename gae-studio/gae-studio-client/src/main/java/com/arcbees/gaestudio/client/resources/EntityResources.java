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
import com.google.gwt.resources.client.CssResource;

public interface EntityResources extends ClientBundle {
    public interface Styles extends CssResource {
        String root();

        String detailHeader();

        String buttonContainer();

        String mainContent();

        String error();

        String editorPanel();

        String edit();

        String fullscreen();

        String form();

        String geopts();
    }

    public interface Editor extends CssResource {
        String clear();

        String checkbox();

        String dropdown();
    }

    @Source({"css/colors.gss", "css/mixins.gss", "css/entity/entity.gss"})
    public Styles styles();

    @Source({"css/colors.gss", "css/mixins.gss", "css/entity/editor.gss"})
    public Editor editor();
}
