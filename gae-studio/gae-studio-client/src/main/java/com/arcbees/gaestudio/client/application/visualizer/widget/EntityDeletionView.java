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
import com.arcbees.gaestudio.client.resources.AppMessages;
import com.arcbees.gaestudio.shared.dispatch.DeleteEntitiesType;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

public class EntityDeletionView extends PopupViewWithUiHandlers<EntityDeletionUiHandlers>
        implements EntityDeletionPresenter.MyView {
    private final AppMessages messages;

    interface Binder extends UiBinder<Widget, EntityDeletionView> {
    }

    @UiField
    Button delete;
    @UiField
    Button cancel;
    @UiField
    HeadingElement message;

    @Inject
    EntityDeletionView(Binder uiBinder,
                       EventBus eventBus,
                       AppMessages messages) {
        super(eventBus);

        this.messages = messages;

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void displayEntityDeletion(ParsedEntity p) {
        message.setInnerText(messages.deleteEntity(p.getKey().getKind(), p.getKey().getId()));

        asPopupPanel().center();
    }

    @Override
    public void displayEntitiesDeletion(DeleteEntitiesType deleteType, String value) {
        String message = "";
        switch (deleteType) {
            case KIND:
                message = messages.deleteEntitiesOfKind(value);
                break;
            case NAMESPACE:
                message = messages.deleteEntitiesOfNamespace(value);
                break;
            case ALL:
                message = messages.deleteAllEntities();
                break;
        }

        this.message.setInnerText(message);
        asPopupPanel().center();
    }

    @UiHandler("delete")
    void onDeletionClicked(ClickEvent event) {
        getUiHandlers().deleteEntity();
        asPopupPanel().hide();
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        getUiHandlers().reset();
        asPopupPanel().hide();
    }
}
