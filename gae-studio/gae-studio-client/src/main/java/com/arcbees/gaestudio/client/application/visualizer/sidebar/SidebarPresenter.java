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
import com.arcbees.gaestudio.client.application.visualizer.event.KindSelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.widget.namespace.DeleteFromNamespaceHandler;
import com.arcbees.gaestudio.client.application.visualizer.widget.namespace.NamespacesListPresenter;
import com.arcbees.gaestudio.client.application.visualizer.widget.namespace.NamespacesListPresenterFactory;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.dispatch.DeleteEntitiesType;
import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsResult;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class SidebarPresenter extends PresenterWidget<SidebarPresenter.MyView> implements SidebarUiHandlers,
        DeleteFromNamespaceHandler {
    interface MyView extends View, HasUiHandlers<SidebarUiHandlers> {
        void updateKinds(List<String> kinds);

        void addEmptyEntityListStyle();
    }

    public static final Object SLOT_NAMESPACES = new Object();

    private final DispatchAsync dispatcher;
    private final NamespacesListPresenter namespacesListPresenter;

    @Inject
    SidebarPresenter(EventBus eventBus,
            MyView view,
            DispatchAsync dispatcher,
            NamespacesListPresenterFactory namespacesListPresenterFactory) {
        super(eventBus, view);

        this.dispatcher = dispatcher;

        namespacesListPresenter = namespacesListPresenterFactory.create(this);

        getView().setUiHandlers(this);
    }

    @Override
    public void onDeleteAllFromNamespace(AppIdNamespaceDto namespaceDto) {
    }

    @Override
    public void displayEntitiesOfSelectedKind(String kind) {
        KindSelectedEvent.fire(this, kind);
    }

    @Override
    public void deleteAll() {
        DeleteEntitiesEvent.fire(this, DeleteEntitiesType.ALL);
    }

    @Override
    protected void onBind() {
        super.onBind();

        setInSlot(SLOT_NAMESPACES, namespacesListPresenter);
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        dispatcher.execute(new GetEntityKindsAction(),
                new AsyncCallbackImpl<GetEntityKindsResult>("Failed getting Entity Kinds: ") {
                    @Override
                    public void onSuccess(GetEntityKindsResult result) {
                        updateKinds(result);
                    }
                });
    }

    private void updateKinds(GetEntityKindsResult result) {
        getView().updateKinds(result.getKinds());
    }
}
