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
import com.google.gwt.dom.client.Style;
import com.google.gwt.query.client.Function;
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
    private final String toolbarStyleName;

    private double kindPanelMargin = 0;
    private boolean fullScreen = false;
    private boolean entityDetailsVisible;

    @Inject
    VisualizerView(Binder uiBinder,
                   AppResources appResources) {
        initWidget(uiBinder.createAndBindUi(this));

        secondTableStyleName = "." + appResources.styles().secondTable();
        toolbarStyleName = "." + appResources.styles().toolbar();
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
        fullScreen = false;
        entityDetailsVisible = true;
        setPanelsWidthPercentages(50, 50);
    }

    @Override
    public void collapseEntityDetails() {
        entityDetailsVisible = false;
        setPanelsWidthPercentages(100, 0);
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
    public void activateFullScreen() {
        fullScreen = true;
        setPanelsWidthPercentages(50, 100);
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
            rightPercentage = fullScreen ? 100 : 50;
        }
        return rightPercentage;
    }

    private void waitForWidgets(final Function callback) {
        if (!(toolbar.isAttached() && entityTypesSidebar.isAttached()) || toolbar.getWidget() == null
                || (entityDetailsVisible && $(secondTableStyleName).widget() == null)) {
            $(asWidget()).delay(1, new Function() {
                @Override
                public void f() {
                    waitForWidgets(callback);
                }
            });
        } else {
            callback.f();
        }
    }

    private void setSidebarMarginLeft(double marginLeft) {
        kindPanelMargin = marginLeft;
        entityTypesSidebar.getElement().getStyle().setMarginLeft(marginLeft, Style.Unit.PX);
    }

    private void setPanelsWidthPercentages(final int leftPercentage, final int rightPercentage) {
        setWidth(getPercentage(leftPercentage), entityListPanel);
        setWidth(getPercentage(rightPercentage), entityDetailsPanel);
        waitForWidgets(new Function() {
            @Override
            public void f() {
                int divisor = rightPercentage == 100 ? 1 : 2;
                setWidth(getWidthString(rightPercentage, getKindBarAndToolBarWidth(divisor)), $(secondTableStyleName).widget());
            }
        });
    }

    private int getKindBarAndToolBarWidth(int divisor) {
        int leftWidth = $(entityTypesSidebar).outerWidth() + $(toolbarStyleName).outerWidth() + (int) kindPanelMargin;

        return leftWidth / divisor + 30;
    }

    private void setWidth(String width, IsWidget widget) {
        $(widget).css("width", width);
    }

    private String getWidthString(int percentage, int offset) {
        return "calc(" + getPercentage(percentage) + " - " + offset + "px)";
    }

    private String getPercentage(int percentage) {
        return percentage + "%";
    }
}
