/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.widget;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import static com.google.gwt.query.client.GQuery.$;

public class HeaderView extends ViewWithUiHandlers<HeaderUiHandlers> implements HeaderPresenter.MyView {
    interface Binder extends UiBinder<Widget, HeaderView> {
    }

    @UiField(provided = true)
    AppResources resources;

    private final String activeStyleName;

    @Inject
    HeaderView(Binder uiBinder,
               AppResources resources) {
        this.resources = resources;

        initWidget(uiBinder.createAndBindUi(this));

        activeStyleName = resources.sprites().topmenuBgActive();
    }

    @Override
    public void activateCurrentLink(String nameTokens) {
        $("." + activeStyleName).removeClass(activeStyleName);
        $("." + nameTokens).addClass(activeStyleName);
    }
}
