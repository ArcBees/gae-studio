/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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

    private boolean isFullscreen;
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
        return entityDetailsVisible && getEntityDetailContent().isEmpty();
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
