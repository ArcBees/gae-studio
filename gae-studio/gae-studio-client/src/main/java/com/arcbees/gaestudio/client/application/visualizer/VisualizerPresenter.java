/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer;

import com.arcbees.gaestudio.client.application.ApplicationPresenter;
import com.arcbees.gaestudio.client.application.event.FullScreenEvent;
import com.arcbees.gaestudio.client.application.event.RowLockedEvent;
import com.arcbees.gaestudio.client.application.event.RowUnlockedEvent;
import com.arcbees.gaestudio.client.application.profiler.widget.ToolbarPresenter;
import com.arcbees.gaestudio.client.application.ui.ToolbarButton;
import com.arcbees.gaestudio.client.application.ui.ToolbarButtonCallback;
import com.arcbees.gaestudio.client.application.ui.UiFactory;
import com.arcbees.gaestudio.client.application.visualizer.event.DeleteEntityEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EditEntityEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntityPageLoadedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.KindPanelToggleEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.KindSelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.SetStateFromPlaceRequestEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.ToolbarToggleEvent;
import com.arcbees.gaestudio.client.application.visualizer.sidebar.SidebarPresenter;
import com.arcbees.gaestudio.client.application.visualizer.widget.EntityListPresenter;
import com.arcbees.gaestudio.client.debug.DebugIds;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.place.ParameterTokens;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.common.collect.Lists;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

public class VisualizerPresenter extends Presenter<VisualizerPresenter.MyView,
        VisualizerPresenter.MyProxy> implements KindSelectedEvent.KindSelectedHandler,
        RowLockedEvent.RowLockedHandler, RowUnlockedEvent.RowUnlockedHandler,
        KindPanelToggleEvent.KindPanelToggleHandler, FullScreenEvent.FullScreenEventHandler,
        EntitySelectedEvent.EntitySelectedHandler, EntityPageLoadedEvent.EntityPageLoadedHandler,
        SetStateFromPlaceRequestEvent.SetStateFromPlaceRequestHandler, ToolbarToggleEvent.ToolbarToggleHandler {
    interface MyView extends View {
        void showEntityDetails();

        void collapseEntityDetails();

        void closeKindPanel();

        void openKindPanel();

        void activateFullScreen();

        void updatePanelsWidth();
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.visualizer)
    interface MyProxy extends ProxyPlace<VisualizerPresenter> {
    }

    @ContentSlot
    public static final GwtEvent.Type<RevealContentHandler<?>> SLOT_ENTITIES = new GwtEvent
            .Type<RevealContentHandler<?>>();
    public static final Object SLOT_TOOLBAR = new Object();
    public static final Object SLOT_KINDS = new Object();
    @ContentSlot
    public static final GwtEvent.Type<RevealContentHandler<?>> SLOT_ENTITY_DETAILS = new GwtEvent
            .Type<RevealContentHandler<?>>();

    private final EntityListPresenter entityListPresenter;
    private final SidebarPresenter sidebarPresenter;
    private final ToolbarButton edit;
    private final ToolbarButton delete;
    private final UiFactory uiFactory;
    private final AppResources resources;
    private final AppConstants myConstants;
    private final ToolbarPresenter toolbarPresenter;

    private ParsedEntity currentParsedEntity;
    private String currentKind = "";

    @Inject
    VisualizerPresenter(EventBus eventBus,
                        MyView view,
                        MyProxy proxy,
                        EntityListPresenter entityListPresenter,
                        SidebarPresenter sidebarPresenter,
                        UiFactory uiFactory,
                        AppResources resources,
                        AppConstants appConstants,
                        ToolbarPresenter toolbarPresenter) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN);

        this.uiFactory = uiFactory;
        this.entityListPresenter = entityListPresenter;
        this.sidebarPresenter = sidebarPresenter;
        this.resources = resources;
        this.myConstants = appConstants;
        this.toolbarPresenter = toolbarPresenter;

        edit = createEditButton();
        delete = createDeleteButton();

        edit.setEnabled(false);
        delete.setEnabled(false);
    }

    @Override
    public void setStateFromPlaceRequest(SetStateFromPlaceRequestEvent event) {
        PlaceRequest placeRequest = event.getPlaceRequest();

        String kindFromPlaceRequest = placeRequest.getParameter(ParameterTokens.KIND, "");
        if (!kindFromPlaceRequest.equals(currentKind)) {
            currentKind = kindFromPlaceRequest;
            updateEntityListPresenter();
        }
    }

    @Override
    public void onRowLocked(RowLockedEvent rowLockedEvent) {
        getView().showEntityDetails();
        enableContextualMenu();
    }

    @Override
    public void onRowUnlocked(RowUnlockedEvent rowLockedEvent) {
        getView().collapseEntityDetails();
        disableContextualMenu();
    }

    @Override
    public void onToolbarToggle(ToolbarToggleEvent event) {
        getView().updatePanelsWidth();
    }

    @Override
    public void onFullScreen(FullScreenEvent event) {
        if (event.isActivate()) {
            getView().activateFullScreen();
        } else {
            getView().showEntityDetails();
        }
    }

    @Override
    public void onKindSelected(KindSelectedEvent event) {
        currentKind = event.getKind();

        getView().collapseEntityDetails();

        updateEntityListPresenter();
    }

    @Override
    public void onKindPanelToggle(KindPanelToggleEvent event) {
        if (event.getAction().equals(KindPanelToggleEvent.Action.CLOSE)) {
            getView().closeKindPanel();
        } else {
            getView().openKindPanel();
        }
    }

    @Override
    public void onEntityPageLoaded(EntityPageLoadedEvent event) {
        disableContextualMenu();
    }

    @Override
    public void onEntitySelected(EntitySelectedEvent event) {
        currentParsedEntity = event.getParsedEntity();
        enableContextualMenu();
    }

    @Override
    protected void onBind() {
        super.onBind();

        setInSlot(SLOT_ENTITIES, entityListPresenter);
        setInSlot(SLOT_TOOLBAR, toolbarPresenter);
        setInSlot(SLOT_KINDS, sidebarPresenter);

        toolbarPresenter.setButtons(Lists.newArrayList(edit, delete));
        toolbarPresenter.setVisible(false);

        addRegisteredHandler(KindSelectedEvent.getType(), this);
        addRegisteredHandler(RowLockedEvent.getType(), this);
        addRegisteredHandler(RowUnlockedEvent.getType(), this);
        addRegisteredHandler(KindPanelToggleEvent.getType(), this);
        addRegisteredHandler(FullScreenEvent.getType(), this);
        addRegisteredHandler(EntitySelectedEvent.getType(), this);
        addRegisteredHandler(EntityPageLoadedEvent.getType(), this);
        addRegisteredHandler(KindSelectedEvent.getType(), this);
        addRegisteredHandler(SetStateFromPlaceRequestEvent.getType(), this);

        addVisibleHandler(ToolbarToggleEvent.getType(), this);
    }

    private ToolbarButton createDeleteButton() {
        return uiFactory.createToolbarButton(myConstants.delete(), resources.styles().delete(),
                new ToolbarButtonCallback() {
                    @Override
                    public void onClicked() {
                        delete();
                    }
                }, DebugIds.DELETE_ENGAGE);
    }

    private void delete() {
        if (currentParsedEntity != null) {
            DeleteEntityEvent.fire(this, currentParsedEntity);
        }
    }

    private ToolbarButton createEditButton() {
        return uiFactory.createToolbarButton(myConstants.edit(), resources.styles().pencil(),
                new ToolbarButtonCallback() {
                    @Override
                    public void onClicked() {
                        edit();
                    }
                }, DebugIds.EDIT);
    }

    private void edit() {
        if (currentParsedEntity != null) {
            EditEntityEvent.fire(this, currentParsedEntity);
        }
    }

    private void updateEntityListPresenter() {
        setInSlot(SLOT_ENTITIES, entityListPresenter);

        if (currentKind.isEmpty()) {
            entityListPresenter.hideList();
        } else {
            entityListPresenter.loadKind(currentKind);
        }

        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                toolbarPresenter.setVisible(!currentKind.isEmpty());
            }
        });
    }

    private void enableContextualMenu() {
        edit.setEnabled(true);
        delete.setEnabled(true);
    }

    private void disableContextualMenu() {
        edit.setEnabled(false);
        delete.setEnabled(false);
    }
}
