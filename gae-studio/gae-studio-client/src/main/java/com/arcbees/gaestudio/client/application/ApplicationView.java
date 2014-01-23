/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application;

import javax.inject.Inject;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

import static com.google.gwt.query.client.GQuery.$;

public class ApplicationView extends ViewImpl implements ApplicationPresenter.MyView, AttachEvent.Handler {
    interface Binder extends UiBinder<Widget, ApplicationView> {
    }

    @UiField
    SimplePanel header;
    @UiField
    SimpleLayoutPanel main;
    @UiField
    SimplePanel messages;
    @UiField
    SimplePanel version;
    @UiField
    HTMLPanel northSection;

    @Inject
    ApplicationView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));

        asWidget().addAttachHandler(this);
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == ApplicationPresenter.SLOT_MAIN) {
            main.setWidget(content);
        } else if (slot == ApplicationPresenter.SLOT_HEADER) {
            header.setWidget(content);
        } else if (slot == ApplicationPresenter.SLOT_MESSAGES) {
            messages.setWidget(content);
        } else if (slot == ApplicationPresenter.SLOT_VERSION) {
            version.setWidget(content);
        } else {
            super.setInSlot(slot, content);
        }
    }

    /**
     * $(northSection).parent() is a <div style="overflow: hidden"/> (generated automatically by GWT's
     * DockPanel system). I override the "overflow" property at runtime because the "overflow: hidden" specificity is
     * too high and cannot be overriden by CSS only.
     * <p/>
     * I also set the z-index to 1 in order to let the Cog wheel's dropdown menu overflow from the header section
     * over the main section
     */
    @Override
    public void onAttachOrDetach(AttachEvent event) {
        if (event.isAttached()) {
            $(northSection).parent().css("overflow", "visible");
            $(northSection).parent().css("z-index", "1");
        }
    }
}
