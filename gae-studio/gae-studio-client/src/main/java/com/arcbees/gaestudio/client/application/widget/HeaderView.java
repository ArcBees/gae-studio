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
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.query.client.Function;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import static com.google.gwt.query.client.GQuery.$;

public class HeaderView extends ViewWithUiHandlers<HeaderUiHandlers> implements HeaderPresenter.MyView,
        AttachEvent.Handler {
    interface Binder extends UiBinder<Widget, HeaderView> {
    }

    @SuppressWarnings("GwtCssResourceErrors")
    interface Styles extends CssResource {
        String activeState();
    }

    @UiField(provided = true)
    AppResources resources;
    @UiField
    DivElement cog;
    @UiField
    UListElement themes;
    @UiField
    Anchor logout;
    @UiField
    Styles style;
    @UiField
    DivElement menu;
    @UiField
    AnchorElement profilerAnchor;

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

        activeStyleName = style.activeState();

        asWidget().addAttachHandler(this);

        $("a", menu).click(new Function() {
            @Override
            public void f() {
                $("." + activeStyleName).removeClass(activeStyleName);
                $(getElement()).addClass(activeStyleName);
            }
        });
    }

    @Override
    public void setProfilerActive() {
        $("." + activeStyleName).removeClass(activeStyleName);
        $(profilerAnchor).addClass(activeStyleName);
    }

    @Override
    public void onAttachOrDetach(AttachEvent event) {
        if (event.isAttached()) {
            $(cog).hover(showThemes, hideThemes);
        }
    }

    @UiHandler("logout")
    void onLogout(ClickEvent event) {
        getUiHandlers().logout();
    }
}
