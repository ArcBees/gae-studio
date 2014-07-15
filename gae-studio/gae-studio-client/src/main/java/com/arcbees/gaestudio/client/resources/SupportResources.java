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
import com.google.gwt.resources.client.ImageResource;

public interface SupportResources extends ClientBundle {
    public interface Styles extends GssResource {
        String support();

        String buttonCancel();

        String buttonContainer();

        String button();
    }

    @Source("images/supportClose.png")
    ImageResource supportClose();

    @Source({"css/colors.css", "css/mixins.css", "css/support.gss"})
    Styles styles();
}
