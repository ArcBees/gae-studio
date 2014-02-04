/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.debug.DebugIds;
import com.arcbees.gaestudio.client.resources.AppMessages;
import com.arcbees.gaestudio.shared.DeleteEntities;
import com.google.common.base.Strings;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
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
    interface Binder extends UiBinder<Widget, EntityDeletionView> {
    }

    @UiField
    Button delete;
    @UiField
    Button cancel;
    @UiField
    HeadingElement message;

    private final AppMessages messages;

    @Inject
    EntityDeletionView(Binder uiBinder,
                       EventBus eventBus,
                       AppMessages messages) {
        super(eventBus);

        this.messages = messages;

        initWidget(uiBinder.createAndBindUi(this));

        delete.ensureDebugId(DebugIds.DELETE_CONFIRM);
    }

    @Override
    public void displayEntityDeletion(ParsedEntity p) {
        message.setInnerSafeHtml(messages.deleteEntity(p.getKey().getKind(), p.getKey().getId()));

        asPopupPanel().center();
    }

    @Override
    public void displayEntitiesDeletion(DeleteEntities deleteType, String kind, String namespace) {
        SafeHtml message = null;
        switch (deleteType) {
            case KIND:
                message = messages.deleteEntitiesOfKind(kind);
                break;
            case NAMESPACE:
                if (Strings.isNullOrEmpty(namespace)) {
                    message = messages.deleteEntitiesOfDefaultNamespace();
                } else {
                    message = messages.deleteEntitiesOfNamespace(namespace);
                }
                break;
            case KIND_NAMESPACE:
                if (Strings.isNullOrEmpty(namespace)) {
                    message = messages.deleteEntitiesOfKindOfDefaultNamespace(kind);
                } else {
                    message = messages.deleteEntitiesOfKindOfNamespace(kind, namespace);
                }
                break;
            case ALL:
                message = messages.deleteAllEntities();
                break;
        }

        this.message.setInnerSafeHtml(message);
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
