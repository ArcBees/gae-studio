/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.widget;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import static com.google.gwt.query.client.GQuery.$;

public class HeaderView extends ViewWithUiHandlers<HeaderUiHandlers> implements HeaderPresenter.MyView,
        AttachEvent.Handler {
    interface Binder extends UiBinder<Widget, HeaderView> {
    }

    @UiField(provided = true)
    AppResources resources;
    @UiField
    DivElement cog;
    @UiField
    UListElement themes;
    @UiField
    DivElement logout;

    private final String activeStyleName;
    private final Function showThemes = new Function() {
        @Override
        public void f() {
            $(themes).show();
        }
    };
    private final Function hideThemes = new Function() {
        @Override
        public void f() {
            $(themes).hide();
        }
    };

    @Inject
    HeaderView(Binder uiBinder,
               AppResources resources) {
        this.resources = resources;

        initWidget(uiBinder.createAndBindUi(this));

        activeStyleName = resources.sprites().topmenuBgActive();

        asWidget().addAttachHandler(this);
    }

    @Override
    public void activateCurrentLink(String nameTokens) {
        $("." + activeStyleName).removeClass(activeStyleName);
        $("." + nameTokens).addClass(activeStyleName);
    }

    @Override
    public void onAttachOrDetach(AttachEvent event) {
        if (event.isAttached()) {
            $(cog).hover(showThemes, hideThemes);

            $(logout).click(new Function() {
                @Override
                public boolean f(Event e) {
                    getUiHandlers().logout();

                    return true;
                }
            });
        }
    }
}
