/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget.namespace;

import java.util.List;

import com.arcbees.gaestudio.client.application.visualizer.event.EntitiesDeletedEvent;
import com.arcbees.gaestudio.client.rest.NamespacesService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.shared.RestDispatch;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import static com.arcbees.gaestudio.client.application.visualizer.event.EntitiesDeletedEvent.EntitiesDeletedHandler;

public class NamespacesListPresenter extends PresenterWidget<NamespacesListPresenter.MyView> implements
        NamespacesListUiHandlers, EntitiesDeletedHandler {
    interface MyView extends View, HasUiHandlers<NamespacesListUiHandlers> {
        void displayNamespaces(List<AppIdNamespaceDto> namespaces);
    }

    private final RestDispatch restDispatch;
    private final NamespacesService namespacesService;
    private final DeleteFromNamespaceHandler deleteHandler;

    @Inject
    NamespacesListPresenter(EventBus eventBus,
                            MyView view,
                            RestDispatch restDispatch,
                            NamespacesService namespacesService,
                            @Assisted DeleteFromNamespaceHandler deleteHandler) {
        super(eventBus, view);

        this.restDispatch = restDispatch;
        this.namespacesService = namespacesService;
        this.deleteHandler = deleteHandler;

        getView().setUiHandlers(this);
    }

    @Override
    public void deleteAllFromNamespace(AppIdNamespaceDto namespaceDto) {
        deleteHandler.onDeleteAllFromNamespace(namespaceDto);
    }

    @Override
    public void onEntitiesDeleted(EntitiesDeletedEvent event) {
        updateNamespaces();
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(EntitiesDeletedEvent.getType(), this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        updateNamespaces();
    }

    private void updateNamespaces() {
        restDispatch.execute(namespacesService.getNamespaces(), new AsyncCallbackImpl<List<AppIdNamespaceDto>>() {
            @Override
            public void onSuccess(List<AppIdNamespaceDto> result) {
                getView().displayNamespaces(result);
            }
        });
    }
}
