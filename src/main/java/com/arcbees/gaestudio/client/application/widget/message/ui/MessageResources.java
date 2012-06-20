/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.client.application.widget.message.ui;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface MessageResources extends ClientBundle {
    public interface Styles extends CssResource {
        String success();

        String error();

        String message();

        String close();
    }

    public Styles styles();

}
