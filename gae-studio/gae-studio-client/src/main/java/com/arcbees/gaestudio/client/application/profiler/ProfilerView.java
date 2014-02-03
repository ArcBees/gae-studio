/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class ProfilerView extends ViewImpl implements ProfilerPresenter.MyView {
    interface Binder extends UiBinder<Widget, ProfilerView> {
    }

    @UiField
    SimplePanel requestPanel;
    @UiField
    SimplePanel statisticsPanel;
    @UiField
    SimplePanel statementPanel;
    @UiField
    SimplePanel toolbarPanel;

    @Inject
    ProfilerView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (content != null) {
            if (slot == ProfilerPresenter.SLOT_REQUESTS) {
                requestPanel.setWidget(content);
            } else if (slot == ProfilerPresenter.SLOT_STATISTICS) {
                statisticsPanel.setWidget(content);
            } else if (slot == ProfilerPresenter.SLOT_STATETEMENTS) {
                statementPanel.setWidget(content);
            } else if (slot == ProfilerPresenter.SLOT_TOOLBAR) {
                toolbarPanel.setWidget(content);
            }
        }
    }
}
