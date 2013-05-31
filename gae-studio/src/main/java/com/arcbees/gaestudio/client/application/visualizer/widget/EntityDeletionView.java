/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

public class EntityDeletionView extends PopupViewWithUiHandlers<EntityDeletionUiHandlers>
        implements EntityDeletionPresenter.MyView {
    interface Binder extends UiBinder<Widget, EntityDeletionView> {
    }

    @UiField
    Button delete;
    @UiField
    PopupPanel popup;
    @UiField
    Button cancel;

    @Inject
    EntityDeletionView(Binder uiBinder, EventBus eventBus) {
        super(eventBus);
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void displayEntityDeletion(ParsedEntity p) {
        popup.center();
    }

    @Override
    public void hide() {
        popup.hide();
    }

    @UiHandler("delete")
    void onDeletionClicked(ClickEvent event) {
        getUiHandlers().deleteEntity();
        hide();
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        hide();
    }
}
