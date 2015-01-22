/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.resources;

import com.arcbees.chosen.client.resources.ChozenCss;
import com.arcbees.chosen.client.resources.Resources;
import com.google.gwt.resources.client.ImageResource;

public interface ChosenResources extends Resources {
    public interface CustomChosenCss extends ChozenCss {
    }

    @Source("images/dropdown/dropDownArrowUp.png")
    ImageResource chosenArrow();

    @Override
    @Source({"com/arcbees/gsss/mixin/client/mixins.gss", "com/arcbees/chosen/client/resources/colors.gss", "com/arcbees/chosen/client/resources/icons/icons.gss", "com/arcbees/chosen/client/resources/chozen.gss", "css/chozen.gss"})
    CustomChosenCss css();
}
