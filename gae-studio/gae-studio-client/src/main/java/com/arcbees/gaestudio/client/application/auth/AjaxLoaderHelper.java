/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.auth;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Image;

import static com.google.gwt.query.client.GQuery.$;

public class AjaxLoaderHelper {
    private final AppResources appResources;

    @Inject
    AjaxLoaderHelper(AppResources appResources) {
        this.appResources = appResources;
    }

    public void showAjaxLoader(Element sibbling) {
        Image ajaxLoader = buildAjaxLoader();

        $(sibbling).before(ajaxLoader.asWidget().getElement());
    }

    public void hideAjaxLoader(Element sibbling) {
        $(sibbling).prev("img").remove();
    }

    private Image buildAjaxLoader() {
        Image ajaxLoader = new Image();
        ajaxLoader.setResource(appResources.ajaxLoader30px());
        ajaxLoader.addStyleName(appResources.styles().loginAjaxLoader());

        return ajaxLoader;
    }
}
