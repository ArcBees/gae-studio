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

package com.arcbees.gaestudio.client.application.visualizer.widget;

import java.util.Set;

import com.arcbees.analytics.shared.Analytics;
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
    private final Analytics analytics;

    @Inject
    EntityDeletionView(
            Binder uiBinder,
            EventBus eventBus,
            AppMessages messages,
            Analytics analytics) {
        super(eventBus);

        this.messages = messages;
        this.analytics = analytics;

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void displayEntityDeletion(ParsedEntity p) {
        message.setHTML(messages.deleteEntity(p.getKey().getKind(), p.getKey().getId()));

        asPopupPanel().center();
    }

    @Override
    public void displayEntitiesDeletion(DeleteEntities deleteType,
            String kind,
            String namespace,
            Set<ParsedEntity> entities) {
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
            case SET:
                message = messages.deleteSelectedEntities(entities.size());
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

        analytics.sendEvent(UI_ELEMENTS, "click")
                .eventLabel("Visualizer -> Delete Confirmation Popup -> Delete");
    }

    @UiHandler({"cancel", "close"})
    void onCancelClicked(ClickEvent event) {
        getUiHandlers().reset();
        asPopupPanel().hide();

        analytics.sendEvent(UI_ELEMENTS, "click")
                .eventLabel("Visualizer -> Delete Confirmation Popup -> Cancel or Close");
    }
}
