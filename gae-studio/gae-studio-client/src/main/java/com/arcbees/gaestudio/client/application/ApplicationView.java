/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class ApplicationView extends ViewImpl implements ApplicationPresenter.MyView {
    interface Binder extends UiBinder<Widget, ApplicationView> {
    }

    @UiField
    SimplePanel header;
    @UiField
    SimplePanel main;
    @UiField
    SimplePanel messages;
    @UiField
    SimplePanel version;
    @UiField
    HTMLPanel northSection;
    @UiField
    HTMLPanel root;

    @Inject
    ApplicationView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
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
}
