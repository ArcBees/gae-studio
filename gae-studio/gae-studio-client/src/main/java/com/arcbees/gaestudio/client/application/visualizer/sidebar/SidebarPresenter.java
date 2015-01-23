/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.sidebar;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import com.arcbees.analytics.shared.Analytics;
import com.arcbees.gaestudio.client.application.analytics.EventCategories;
import com.arcbees.gaestudio.client.application.channel.ChannelHandler;
import com.arcbees.gaestudio.client.application.event.ImportCompletedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.DeleteEntitiesEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitiesDeletedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.KindPanelToggleEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.KindSelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.NamespaceSelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.widget.ImportPresenter;
import com.arcbees.gaestudio.client.application.visualizer.widget.namespace.DeleteFromNamespaceHandler;
import com.arcbees.gaestudio.client.application.visualizer.widget.namespace.NamespacesListPresenter;
import com.arcbees.gaestudio.client.application.visualizer.widget.namespace.NamespacesListPresenterFactory;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.rest.ExportService;
import com.arcbees.gaestudio.client.rest.KindsService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.DeleteEntities;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.client.RestDispatch;
import com.gwtplatform.dispatch.rest.shared.RestAction;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

import static com.arcbees.gaestudio.client.application.analytics.EventCategories.UI_ELEMENTS;
import static com.arcbees.gaestudio.client.application.visualizer.event.EntitiesDeletedEvent.EntitiesDeletedHandler;
import static com.arcbees.gaestudio.client.application.visualizer.event.KindPanelToggleEvent.Action.CLOSE;
import static com.arcbees.gaestudio.client.application.visualizer.event.KindPanelToggleEvent.Action.OPEN;

public class SidebarPresenter extends PresenterWidget<SidebarPresenter.MyView> implements SidebarUiHandlers,
        DeleteFromNamespaceHandler, EntitiesDeletedHandler, ImportCompletedEvent.ImportCompletedHandler,
        ValueChangeHandler<AppIdNamespaceDto> {
    interface MyView extends View, HasUiHandlers<SidebarUiHandlers> {
        void updateKinds(List<String> kinds);

        void addEmptyEntityListStyle();

        void setDownloadUrl(String downloadUrl);

        void setSelectedFormat();
    }

    public static final Object SLOT_NAMESPACES = new Object();

    private final PlaceManager placeManager;
    private final RestDispatch restDispatch;
    private final KindsService kindsService;
    private final ExportService exportService;
    private final Provider<ImportPresenter> importPresenterProvider;
    private final NamespacesListPresenter namespacesListPresenter;
    private final Analytics analytics;
    private final ChannelHandler channelHandler;

    private KindPanelToggleEvent.Action action = CLOSE;
    private String currentKind;
    private String currentNamespace;

    @Inject
    SidebarPresenter(EventBus eventBus,
                     MyView view,
                     PlaceManager placeManager,
                     RestDispatch restDispatch,
                     KindsService kindsService,
                     ExportService exportService,
                     Provider<ImportPresenter> importPresenterProvider,
                     NamespacesListPresenterFactory namespacesListPresenterFactory,
                     Analytics analytics,
                     ChannelHandler channelHandler) {
        super(eventBus, view);

        this.placeManager = placeManager;
        this.restDispatch = restDispatch;
        this.kindsService = kindsService;
        this.exportService = exportService;
        this.importPresenterProvider = importPresenterProvider;
        this.analytics = analytics;
        this.channelHandler = channelHandler;
        namespacesListPresenter = namespacesListPresenterFactory.create(this);
        namespacesListPresenter.addValueChangeHandler(this);

        getView().setUiHandlers(this);
    }

    @Override
    public void onDeleteAllFromNamespace(AppIdNamespaceDto namespaceDto) {
        if (namespaceDto == null) {
            DeleteEntitiesEvent.fire(this, DeleteEntities.ALL);
        } else {
            DeleteEntitiesEvent.fire(this, DeleteEntities.NAMESPACE, "", namespaceDto.getNamespace());
        }

        analytics.sendEvent(UI_ELEMENTS, "click")
                .eventLabel("Visualizer -> Kinds sidebar -> Delete All Entities Button");
    }

    @Override
    public void onEntitiesDeleted(EntitiesDeletedEvent event) {
        updateKinds();
    }

    @Override
    public void importKind() {
        addToPopupSlot(importPresenterProvider.get());
    }

    @Override
    public void importCsv() {
        //TODO : Import CSV
    }

    @Override
    public void deleteAllOfKind() {
        DeleteEntitiesEvent.fire(this, DeleteEntities.KIND, currentKind);
    }

    @Override
    public void displayEntitiesOfSelectedKind(String kind) {
        currentKind = kind;

        PlaceRequest placeRequest = new PlaceRequest.Builder()
                .nameToken(NameTokens.visualizer)
                .with(UrlParameters.KIND, currentKind)
                .build();
        placeManager.revealPlace(placeRequest);

        KindSelectedEvent.fire(this, kind);
    }

    @Override
    public void onCloseHandleActivated() {
        getEventBus().fireEvent(new KindPanelToggleEvent(action));

        if (action.equals(CLOSE)) {
            action = OPEN;
        } else {
            action = CLOSE;
        }
    }

    @Override
    public void exportJson() {
        channelHandler.openChannel(new ChannelHandler.ChannelOpenCallback() {
            @Override
            public void onChannelOpen() {
                String exportKindUrl = exportService
                        .getExportJson(currentKind, currentNamespace, extractKeysFromPlace());

                getView().setDownloadUrl(exportKindUrl);
            }
        });
    }

    @Override
    public void exportCsv() {
        channelHandler.openChannel(new ChannelHandler.ChannelOpenCallback() {
            @Override
            public void onChannelOpen() {
                String exportCsvUrl = exportService.getExportCsv(currentKind, currentNamespace, extractKeysFromPlace());

                getView().setDownloadUrl(exportCsvUrl);
            }
        });
    }

    @Override
    public void onImportComplete(ImportCompletedEvent event) {
        updateKinds();
    }

    @Override
    public void onValueChange(ValueChangeEvent<AppIdNamespaceDto> event) {
        AppIdNamespaceDto appIdNamespace = event.getValue();

        RestAction<List<String>> getKindsAction;
        if (appIdNamespace == null) {
            currentNamespace = null;
            getKindsAction = kindsService.getKinds();
        } else {
            currentNamespace = appIdNamespace.getNamespace();
            getKindsAction = kindsService.getKinds(currentNamespace);
        }

        NamespaceSelectedEvent.fire(this, currentNamespace);

        restDispatch.execute(getKindsAction, new AsyncCallbackImpl<List<String>>() {
            @Override
            public void onSuccess(List<String> result) {
                onKindsUpdated(result);
            }
        });

        analytics.sendEvent(EventCategories.UI_ELEMENTS, "value changed")
                .eventLabel("Visualizer -> Kinds Sidebar -> Kinds");
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(EntitiesDeletedEvent.getType(), this);
        addRegisteredHandler(ImportCompletedEvent.getType(), this);

        setInSlot(SLOT_NAMESPACES, namespacesListPresenter);
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        updateKinds();

        getView().setSelectedFormat();
    }

    @Override
    protected void onHide() {
        super.onHide();

        getView().setDownloadUrl("");
    }

    private String extractKeysFromPlace() {
        PlaceRequest currentPlaceRequest = placeManager.getCurrentPlaceRequest();

        return currentPlaceRequest.getParameter(UrlParameters.KEY, null);
    }

    private void updateKinds() {
        restDispatch.execute(kindsService.getKinds(),
                new AsyncCallbackImpl<List<String>>("Failed getting Entity Kinds: ") {
                    @Override
                    public void onSuccess(List<String> result) {
                        onKindsUpdated(result);
                    }
                });
    }

    private void onKindsUpdated(List<String> kinds) {
        getView().updateKinds(kinds);
        if (!kinds.contains(currentKind)) {
            placeManager.revealPlace(new PlaceRequest.Builder().nameToken(NameTokens.visualizer).build());
            currentKind = null;
            KindSelectedEvent.fire(this, null);
        }
    }
}
