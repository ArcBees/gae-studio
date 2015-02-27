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

package com.arcbees.gaestudio.client.application.visualizer.widget.namespace;

import java.util.List;

import com.arcbees.gaestudio.client.application.visualizer.event.EntitiesDeletedEvent;
import com.arcbees.gaestudio.client.rest.NamespacesService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.client.RestDispatch;
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
    NamespacesListPresenter(
            EventBus eventBus,
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
    public void dropdownValueChanged(ValueChangeEvent<AppIdNamespaceDto> event) {
        fireEvent(event);
    }

    public void addValueChangeHandler(ValueChangeHandler<AppIdNamespaceDto> handler) {
        addRegisteredHandler(ValueChangeEvent.getType(), handler);
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
