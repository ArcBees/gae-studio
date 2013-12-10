/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.gaestudio.client.application.ui.ToolbarButton;
import com.arcbees.gaestudio.client.application.ui.ToolbarButtonCallback;
import com.arcbees.gaestudio.client.application.ui.UiFactory;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.client.util.DebugIds;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import static com.google.gwt.query.client.GQuery.$;

public class VisualizerToolbarView extends ViewWithUiHandlers<VisualizerToolbarUiHandlers> implements
        VisualizerToolbarPresenter.MyView {
    interface Binder extends UiBinder<Widget, VisualizerToolbarView> {
    }

    @UiField(provided = true)
    AppResources resources;
    @UiField
    HTMLPanel buttons;

    private final UiFactory uiFactory;
    private final AppConstants myConstants;
    private final ToolbarButton refresh;
    private final ToolbarButton edit;
    private final ToolbarButton delete;
    private final SimplePanel deleteByKind;
    private final String secondTableStyleName;
    private final String secondTableHiddenStyleName;
    private final String entityListContainerSelectedStyleName;
    private final String namespaceStyleName;
    private final String idStyleName;
    private final String entityStyleName;
    private final String backButtonStyleName;

    @Inject
    VisualizerToolbarView(Binder uiBinder,
                          AppResources resources,
                          UiFactory uiFactory,
                          AppConstants myConstants) {
        this.resources = resources;
        this.uiFactory = uiFactory;
        this.myConstants = myConstants;

        initWidget(uiBinder.createAndBindUi(this));

        secondTableStyleName = resources.styles().secondTable();
        secondTableHiddenStyleName = resources.styles().secondTableHidden();
        entityListContainerSelectedStyleName = resources.styles().entityListContainerSelected();
        namespaceStyleName = resources.styles().namespace();
        idStyleName = resources.styles().idBold();
        entityStyleName = resources.styles().isDisplayingEntity();
        backButtonStyleName = resources.styles().backButton();

        refresh = createRefreshButton();
        edit = createEditButton();
        delete = createDeleteButton();
        deleteByKind = new SimplePanel();
        deleteByKind.setStyleName(resources.styles().toolbarButton());

        buttons.add(refresh);
        buttons.add(edit);
        buttons.add(delete);
        buttons.add(deleteByKind);

        edit.setEnabled(false);
        delete.setEnabled(false);
    }

    @Override
    public void setKindSelected(boolean isSelected) {
        buttons.setVisible(isSelected);
    }

    @Override
    public void enableContextualMenu() {
        edit.setEnabled(true);
        delete.setEnabled(true);
    }

    @Override
    public void disableContextualMenu() {
        edit.setEnabled(false);
        delete.setEnabled(false);
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (VisualizerToolbarPresenter.SLOT_NAMESPACES == slot) {
            deleteByKind.setWidget(content);
        } else {
            super.setInSlot(slot, content);
        }
    }

    private ToolbarButton createRefreshButton() {
        return uiFactory.createToolbarButton(myConstants.refresh(), resources.refresh(), new ToolbarButtonCallback() {
            @Override
            public void onClicked() {
                getUiHandlers().refresh();
                $("." + secondTableStyleName).addClass(secondTableHiddenStyleName);
                $("." + entityListContainerSelectedStyleName).removeClass(entityListContainerSelectedStyleName);
                $("." + namespaceStyleName).hide();
                $("." + entityStyleName).hide();
                $("." + idStyleName).text("no entity");
                $("." + backButtonStyleName).hide();
            }
        });
    }

    private ToolbarButton createEditButton() {
        return uiFactory.createToolbarButton(myConstants.edit(), resources.edit(), new ToolbarButtonCallback() {
            @Override
            public void onClicked() {
                getUiHandlers().edit();
            }
        }, DebugIds.EDIT);
    }

    private ToolbarButton createDeleteButton() {
        return uiFactory.createToolbarButton(myConstants.delete(), resources.delete(), new ToolbarButtonCallback() {
            @Override
            public void onClicked() {
                getUiHandlers().delete();
            }
        });
    }
}
