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
import com.google.gwt.resources.client.GssResource;

public interface EntityResources extends ClientBundle {
    public interface Styles extends GssResource {
        String root();

        String detailHeader();

        String buttonContainer();

        String mainContent();

        String error();

        String editorPanel();

        String edit();

        String fullscreen();
    }

    public interface Editor extends GssResource {
        String clear();

        String checkbox();

        String dropdown();
    }

    @Source({"css/colors.css", "css/mixins.css", "css/entity/entity.css"})
    public Styles styles();

    @Source({"css/colors.css", "css/mixins.css", "css/entity/editor.css"})
    public Editor editor();
}