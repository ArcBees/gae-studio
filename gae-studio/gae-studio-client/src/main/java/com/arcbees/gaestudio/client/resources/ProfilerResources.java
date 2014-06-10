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

public interface ProfilerResources extends ClientBundle {
    public interface Styles extends GssResource {
        String root();

        String toolbar();

        String splitLayoutPanel();

        String leftPanel();

        String lightGrey();

        String mediumGrey();

        String whitePanel();

        String buttons();

        String panel();

        String listGraph();

        String statements();

        String filter();

        String content();

        String whiteLine();

        String ui();
    }

    @Source({"css/colors.css", "css/mixins.css", "css/profiler/profiler.css"})
    public Styles styles();
}
