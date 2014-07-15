/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.analytics.client.universalanalytics.UniversalAnalytics;
import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.resources.AppMessages;
import com.arcbees.gaestudio.shared.DeleteEntities;
import com.google.common.base.Strings;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

import static com.arcbees.gaestudio.client.application.analytics.EventCategories.UI_ELEMENTS;

public class EntityDeletionView extends PopupViewWithUiHandlers<EntityDeletionUiHandlers>
        implements EntityDeletionPresenter.MyView {
    interface Binder extends UiBinder<Widget, EntityDeletionView> {
    }

    @UiField
    Button delete;
    @UiField
    Button cancel;
    @UiField
    HTML message;

    private final AppMessages messages;
    private final UniversalAnalytics universalAnalytics;

    @Inject
    EntityDeletionView(Binder uiBinder,
                       EventBus eventBus,
                       AppMessages messages,
                       UniversalAnalytics universalAnalytics) {
        super(eventBus);

        this.messages = messages;
        this.universalAnalytics = universalAnalytics;

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void displayEntityDeletion(ParsedEntity p) {
        message.setHTML(messages.deleteEntity(p.getKey().getKind(), p.getKey().getId()));

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

        this.message.setHTML(message);
        asPopupPanel().center();
    }

    @UiHandler("delete")
    void onDeletionClicked(ClickEvent event) {
        getUiHandlers().deleteEntity();
        asPopupPanel().hide();

        universalAnalytics.sendEvent(UI_ELEMENTS, "click")
                .eventLabel("Visualizer -> Delete Confirmation Popup -> Delete");
    }

    @UiHandler({"cancel", "close"})
    void onCancelClicked(ClickEvent event) {
        getUiHandlers().reset();
        asPopupPanel().hide();

        universalAnalytics.sendEvent(UI_ELEMENTS, "click")
                .eventLabel("Visualizer -> Delete Confirmation Popup -> Cancel or Close");
    }
}
