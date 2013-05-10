/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.resources;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.SimplePager;

public interface PagerResources extends SimplePager.Resources {
    public interface PageStyles extends SimplePager.Style {
        @Override
        String pageDetails();
    }

    @Override
    PageStyles simplePagerStyle();

    @Override
    ImageResource simplePagerPreviousPage();

    @Override
    ImageResource simplePagerPreviousPageDisabled();

    @Override
    ImageResource simplePagerNextPageDisabled();

    @Override
    ImageResource simplePagerNextPage();

    @Override
    ImageResource simplePagerLastPageDisabled();

    @Override
    ImageResource simplePagerLastPage();

    @Override
    ImageResource simplePagerFirstPageDisabled();

    @Override
    ImageResource simplePagerFirstPage();
}
