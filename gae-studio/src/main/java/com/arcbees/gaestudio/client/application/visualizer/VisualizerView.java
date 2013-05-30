/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

import static com.google.gwt.query.client.GQuery.$;

public class VisualizerView extends ViewImpl implements VisualizerPresenter.MyView, AttachEvent.Handler {
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

    private final String noOverflowStyleName;
    private final String entityListContainerSelectedStyleName;
    private final String backButtonStyleName;

    @Inject
    VisualizerView(Binder uiBinder,
                   AppResources appResources) {
        initWidget(uiBinder.createAndBindUi(this));

        noOverflowStyleName = appResources.styles().noOverflow();
        entityListContainerSelectedStyleName = appResources.styles().entityListContainerSelected();
        backButtonStyleName = appResources.styles().backButton();

        asWidget().addAttachHandler(this);
    }

    @Override
    public void onAttachOrDetach(AttachEvent attachEvent) {
        if (attachEvent.isAttached()) {
            bindGwtQuery();
        }
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

    private void bindGwtQuery() {
        $("." + backButtonStyleName).click(new Function() {
            @Override
            public void f() {
                $("." + entityListContainerSelectedStyleName).removeClass(entityListContainerSelectedStyleName);
            }
        });

        $("." + noOverflowStyleName).css("overflow", "visible");
        $("." + noOverflowStyleName).parent("div").css("overflow", "visible");
        $("." + noOverflowStyleName).parents("div").css("overflow", "visible");
        $("." + noOverflowStyleName).parents("div").parents("div").css("overflow", "visible");
    }
}
