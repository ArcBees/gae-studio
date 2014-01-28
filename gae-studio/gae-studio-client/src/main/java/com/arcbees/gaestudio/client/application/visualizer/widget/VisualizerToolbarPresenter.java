/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.application.visualizer.event.DeleteEntitiesEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.DeleteEntityEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EditEntityEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntityPageLoadedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.KindSelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.RefreshEntitiesEvent;
import com.arcbees.gaestudio.client.application.visualizer.widget.namespace.DeleteFromNamespaceHandler;
import com.arcbees.gaestudio.client.application.visualizer.widget.namespace.NamespacesListPresenter;
import com.arcbees.gaestudio.client.application.visualizer.widget.namespace.NamespacesListPresenterFactory;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.rest.EntitiesService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.DeleteEntities;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.shared.RestDispatch;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import static com.arcbees.gaestudio.client.application.visualizer.event.EntityPageLoadedEvent.EntityPageLoadedHandler;
import static com.arcbees.gaestudio.client.application.visualizer.event.EntitySelectedEvent.EntitySelectedHandler;
import static com.arcbees.gaestudio.client.application.visualizer.event.KindSelectedEvent.KindSelectedHandler;

public class VisualizerToolbarPresenter extends PresenterWidget<VisualizerToolbarPresenter.MyView> implements
        VisualizerToolbarUiHandlers, KindSelectedHandler, EntitySelectedHandler, EntityPageLoadedHandler,
        DeleteFromNamespaceHandler {
    interface MyView extends View, HasUiHandlers<VisualizerToolbarUiHandlers> {
        void setKindSelected(boolean isSelected);

        void enableContextualMenu();

        void disableContextualMenu();
    }

    public static final Object SLOT_NAMESPACES = new Object();

    private final AppConstants myConstants;
    private final NamespacesListPresenter namespacesListPresenter;
    private final EntitiesService entitiesService;
    private final RestDispatch restDispatch;

    private String currentKind = "";
    private ParsedEntity currentParsedEntity;

    @Inject
    VisualizerToolbarPresenter(EventBus eventBus,
                               MyView view,
                               NamespacesListPresenterFactory namespacesListPresenterFactory,
                               RestDispatch restDispatch,
                               EntitiesService entitiesService,
                               AppConstants myConstants) {
        super(eventBus, view);

        getView().setUiHandlers(this);

        this.restDispatch = restDispatch;
        this.entitiesService = entitiesService;
        this.myConstants = myConstants;
        namespacesListPresenter = namespacesListPresenterFactory.create(this);
    }

    @Override
    public void onDeleteAllFromNamespace(AppIdNamespaceDto namespaceDto) {
        if (!Strings.isNullOrEmpty(currentKind)) {
            if (namespaceDto == null) {
                DeleteEntitiesEvent.fire(this, DeleteEntities.KIND, currentKind);
            } else {
                DeleteEntitiesEvent.fire(this, DeleteEntities.KIND_NAMESPACE, currentKind, namespaceDto.getNamespace());
            }
        }
    }

    @Override
    public void refresh() {
        RefreshEntitiesEvent.fire(this);
    }

    @Override
    public void create() {
        if (!currentKind.isEmpty()) {
            restDispatch.execute(entitiesService.createByKind(currentKind),
                    new AsyncCallbackImpl<EntityDto>(myConstants.errorUnableToGenerateEmptyJson()) {
                        @Override
                        public void onSuccess(EntityDto emptyEntityDto) {
                            EditEntityEvent.fire(VisualizerToolbarPresenter.this, new ParsedEntity(emptyEntityDto));
                        }
                    });
        }
    }

    @Override
    public void edit() {
        if (currentParsedEntity != null) {
            EditEntityEvent.fire(this, currentParsedEntity);
        }
    }

    @Override
    public void delete() {
        if (currentParsedEntity != null) {
            DeleteEntityEvent.fire(this, currentParsedEntity);
        }
    }

    @Override
    public void onKindSelected(KindSelectedEvent event) {
        currentKind = event.getKind();
        getView().setKindSelected(!currentKind.isEmpty());
    }

    @Override
    public void onEntitySelected(EntitySelectedEvent event) {
        currentParsedEntity = event.getParsedEntity();
        getView().enableContextualMenu();
    }

    @Override
    public void onEntityPageLoaded(EntityPageLoadedEvent event) {
        getView().disableContextualMenu();
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(KindSelectedEvent.getType(), this);
        addRegisteredHandler(EntitySelectedEvent.getType(), this);
        addRegisteredHandler(EntityPageLoadedEvent.getType(), this);

        setInSlot(SLOT_NAMESPACES, namespacesListPresenter);
    }
}
