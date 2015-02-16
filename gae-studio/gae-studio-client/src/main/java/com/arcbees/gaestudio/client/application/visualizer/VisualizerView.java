/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.dom.client.Style;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
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

    private final String secondTableStyleName;

    private boolean isFullscreen = false;
    private boolean entityDetailsVisible;

    @Inject
    VisualizerView(
            Binder uiBinder,
            AppResources appResources) {
        initWidget(uiBinder.createAndBindUi(this));

        secondTableStyleName = "." + appResources.styles().secondTable();
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
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

    @Override
    public void showEntityDetails() {
        isFullscreen = true;
        entityDetailsVisible = true;
        setPanelsWidthPercentages(50, 100);
    }

    @Override
    public void collapseEntityDetails() {
        if (getRightPanelPercentage() > 0) {
            entityDetailsVisible = false;
            setPanelsWidthPercentages(100, 0);
        }
    }

    @Override
    public void closeKindPanel() {
        setSidebarMarginLeft(-230d);
        updatePanelsWidth();
    }

    @Override
    public void openKindPanel() {
        setSidebarMarginLeft(0d);
        updatePanelsWidth();
    }

    @Override
    public void updatePanelsWidth() {
        int rightPercentage = getRightPanelPercentage();
        int leftPercentage = 100 - rightPercentage;

        setPanelsWidthPercentages(leftPercentage, rightPercentage);
    }

    private int getRightPanelPercentage() {
        int rightPercentage = 0;
        if (entityDetailsVisible) {
            rightPercentage = isFullscreen ? 100 : 50;
        }

        return rightPercentage;
    }

    private void waitForWidgets(final Function callback) {
        if (widgetsAreNotReady()) {
            $(entityDetailsPanel).delay(100, new Function() {
                @Override
                public void f() {
                    waitForWidgets(callback);
                }
            });
        } else {
            callback.f();
        }
    }

    private boolean widgetsAreNotReady() {
        return !toolbarAndSidebarAreAttached() || toolbar.getWidget() == null || entityDetailsNotAttached();
    }

    private boolean entityDetailsNotAttached() {
        return (entityDetailsVisible && getEntityDetailContent().isEmpty());
    }

    private boolean toolbarAndSidebarAreAttached() {
        return toolbar.isAttached() && entityTypesSidebar.isAttached();
    }

    private void setSidebarMarginLeft(double marginLeft) {
        entityTypesSidebar.getElement().getStyle().setMarginLeft(marginLeft, Style.Unit.PX);
    }

    private void setPanelsWidthPercentages(final int leftPercentage, final int rightPercentage) {
        setWidth(getPercentage(leftPercentage), $(entityListPanel));
        setWidth(getPercentage(rightPercentage), $(entityDetailsPanel));
        waitForWidgets(new Function() {
            @Override
            public void f() {
                setWidth(getWidthString(rightPercentage, 45), getEntityDetailContent());
            }
        });
    }

    private void setWidth(String width, GQuery widget) {
        $(widget).css("width", width);
    }

    private String getWidthString(int percentage, int offset) {
        return "calc(" + getPercentage(percentage) + " - " + offset + "px)";
    }

    private String getPercentage(int percentage) {
        return percentage + "%";
    }

    private GQuery getEntityDetailContent() {
        return $(secondTableStyleName);
    }
}
