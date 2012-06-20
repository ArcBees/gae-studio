/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface Resources extends ClientBundle {

    public interface Styles extends CssResource {
        String list();
        
        String item();
        
        String selected();

        String fleft();

        String fright();

        String clear();
    }

    public Styles styles();

}
