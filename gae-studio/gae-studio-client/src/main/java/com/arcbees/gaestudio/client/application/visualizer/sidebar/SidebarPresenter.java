/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.sidebar;

import java.util.List;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.visualizer.event.DeleteEntitiesEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitiesDeletedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.KindPanelToggleEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.KindSelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.widget.namespace.DeleteFromNamespaceHandler;
import com.arcbees.gaestudio.client.application.visualizer.widget.namespace.NamespacesListPresenter;
import com.arcbees.gaestudio.client.application.visualizer.widget.namespace.NamespacesListPresenterFactory;
import com.arcbees.gaestudio.client.rest.KindsService;
import com.arcbees.gaestudio.client.util.MethodCallbackImpl;
import com.arcbees.gaestudio.shared.DeleteEntities;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import static com.arcbees.gaestudio.client.application.visualizer.event.EntitiesDeletedEvent.EntitiesDeletedHandler;
import static com.arcbees.gaestudio.client.application.visualizer.event.KindPanelToggleEvent.Action.CLOSE;
import static com.arcbees.gaestudio.client.application.visualizer.event.KindPanelToggleEvent.Action.OPEN;

public class SidebarPresenter extends PresenterWidget<SidebarPresenter.MyView> implements SidebarUiHandlers,
        DeleteFromNamespaceHandler, EntitiesDeletedHandler {
    interface MyView extends View, HasUiHandlers<SidebarUiHandlers> {
        void updateKinds(List<String> kinds);

        void addEmptyEntityListStyle();

        void showCloseHandle();
    }

    public static final Object SLOT_NAMESPACES = new Object();

    private final KindsService kindsService;
    private final NamespacesListPresenter namespacesListPresenter;
    private KindPanelToggleEvent.Action action = CLOSE;

    @Inject
    SidebarPresenter(EventBus eventBus,
                     MyView view,
                     KindsService kindsService,
                     NamespacesListPresenterFactory namespacesListPresenterFactory) {
        super(eventBus, view);

        this.kindsService = kindsService;
        namespacesListPresenter = namespacesListPresenterFactory.create(this);

        getView().setUiHandlers(this);
    }

    @Override
    public void onDeleteAllFromNamespace(AppIdNamespaceDto namespaceDto) {
        if (namespaceDto == null) {
            DeleteEntitiesEvent.fire(this, DeleteEntities.ALL);
        } else {
            DeleteEntitiesEvent.fire(this, DeleteEntities.NAMESPACE, "", namespaceDto.getNamespace());
        }
    }

    @Override
    public void onEntitiesDeleted(EntitiesDeletedEvent event) {
        updateKinds();
    }

    @Override
    public void displayEntitiesOfSelectedKind(String kind) {
        KindSelectedEvent.fire(this, kind);

        allowClosingSidebar();
    }

    @Override
    public void onCloseHandleActivated() {
        getEventBus().fireEvent(new KindPanelToggleEvent(action));

        if(action.equals(CLOSE)) {
            action = OPEN;
        } else {
            action = CLOSE;
        }
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(EntitiesDeletedEvent.getType(), this);

        setInSlot(SLOT_NAMESPACES, namespacesListPresenter);
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        updateKinds();
    }

    private void allowClosingSidebar() {
        getView().showCloseHandle();
    }

    private void updateKinds() {
        kindsService.getKinds(new MethodCallbackImpl<List<String>>("Failed getting Entity Kinds: ") {
            @Override
            public void onSuccess(List<String> result) {
                onKindsUpdated(result);
            }
        });
    }

    private void onKindsUpdated(List<String> kinds) {
        getView().updateKinds(kinds);
    }
}
