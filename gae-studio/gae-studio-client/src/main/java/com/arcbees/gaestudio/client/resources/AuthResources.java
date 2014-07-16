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

public interface AuthResources extends ClientBundle {
    public interface Styles extends GssResource {
        String separator();

        String buttonContainer();

        String wrapper();

        String paragraph();

        String loginLink();

        String root();

        String wrap();

        String main();

        String topBlueBar();

        String middleBlueBar();

        String logo();

        String errorMessage();

        String footer();

        String formTitle();

        String ajaxContainer();

        String authForm();
    }

    @Source({"css/colors.gss", "css/mixins.gss", "css/auth/auth.gss"})
    public Styles styles();
}
