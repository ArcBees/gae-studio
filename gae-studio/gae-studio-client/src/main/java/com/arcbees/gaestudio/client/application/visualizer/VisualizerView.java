/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

import static com.google.gwt.query.client.GQuery.$;

public class VisualizerView extends ViewImpl implements VisualizerPresenter.MyView {
    interface Binder extends UiBinder<Widget, VisualizerView> {
    }

    @UiField
    SimplePanel entityListPanel;
    @UiField
    SimplePanel toolbar;
    @UiField
    SimplePanel entityTypesSidebar;
    @UiField
    SimplePanel entityDetailsPanel;

    @Inject
    VisualizerView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (content != null) {
            if (slot == VisualizerPresenter.SLOT_ENTITIES) {
                entityListPanel.setWidget(content);
            } else if (slot == VisualizerPresenter.SLOT_TOOLBAR) {
                toolbar.setWidget(content);
            } else if (slot == VisualizerPresenter.SLOT_KINDS) {
                entityTypesSidebar.setWidget(content);
            } else if (slot == VisualizerPresenter.SLOT_ENTITY_DETAILS) {
                entityDetailsPanel.setWidget(content);
            }
        }
    }

    @Override
    public void setPanelsWidthPercentages(int leftPercentage, int rightPercentage) {
        setWidthPercentage(leftPercentage, entityListPanel);
        setWidthPercentage(rightPercentage, entityDetailsPanel);
    }

    private void setWidthPercentage(int percentage, IsWidget widget) {
        $(widget).css("width", percentage + "%");
    }
}
