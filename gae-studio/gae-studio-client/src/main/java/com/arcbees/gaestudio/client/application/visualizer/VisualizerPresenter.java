/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer;

import java.util.Set;

import com.arcbees.analytics.client.universalanalytics.UniversalAnalytics;
import com.arcbees.gaestudio.client.application.ApplicationPresenter;
import com.arcbees.gaestudio.client.application.event.FullScreenEvent;
import com.arcbees.gaestudio.client.application.event.RowLockedEvent;
import com.arcbees.gaestudio.client.application.event.RowUnlockedEvent;
import com.arcbees.gaestudio.client.application.profiler.widget.ToolbarPresenter;
import com.arcbees.gaestudio.client.application.ui.ToolbarButton;
import com.arcbees.gaestudio.client.application.ui.ToolbarButtonCallback;
import com.arcbees.gaestudio.client.application.ui.UiFactory;
import com.arcbees.gaestudio.client.application.visualizer.event.DeleteEntitiesEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.DeleteEntityEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.DeselectEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EditEntitiesEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitiesSelectedEvent;
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
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.resources.FontsResources;
import com.arcbees.gaestudio.shared.DeleteEntities;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
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
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

import static com.arcbees.gaestudio.client.application.analytics.EventCategories.UI_ELEMENTS;

public class VisualizerPresenter extends Presenter<VisualizerPresenter.MyView,
        VisualizerPresenter.MyProxy> implements KindSelectedEvent.KindSelectedHandler,
        RowLockedEvent.RowLockedHandler, RowUnlockedEvent.RowUnlockedHandler,
        KindPanelToggleEvent.KindPanelToggleHandler, FullScreenEvent.FullScreenEventHandler,
        EntitySelectedEvent.EntitySelectedHandler, EntitiesSelectedEvent.EntitySelectedHandler,
        EntityPageLoadedEvent.EntityPageLoadedHandler, SetStateFromPlaceRequestEvent.SetStateFromPlaceRequestHandler,
        ToolbarToggleEvent.ToolbarToggleHandler {
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
    public static final GwtEvent.Type<RevealContentHandler<?>> SLOT_ENTITIES = new GwtEvent.Type<>();
    @ContentSlot
    public static final GwtEvent.Type<RevealContentHandler<?>> SLOT_ENTITY_DETAILS = new GwtEvent.Type<>();
    public static final Object SLOT_TOOLBAR = new Object();
    public static final Object SLOT_KINDS = new Object();

    private final EntityListPresenter entityListPresenter;
    private final SidebarPresenter sidebarPresenter;
    private final ToolbarButton edit;
    private final ToolbarButton delete;
    private final ToolbarButton deselect;
    private final UiFactory uiFactory;
    private final PlaceManager placeManager;
    private final FontsResources fontsResources;
    private final AppConstants myConstants;
    private final ToolbarPresenter toolbarPresenter;
    private final UniversalAnalytics universalAnalytics;

    private ParsedEntity currentParsedEntity;
    private Set<ParsedEntity> currentParsedEntities;
    private String currentKind = "";

    @Inject
    VisualizerPresenter(EventBus eventBus,
                        MyView view,
                        MyProxy proxy,
                        EntityListPresenter entityListPresenter,
                        SidebarPresenter sidebarPresenter,
                        UiFactory uiFactory,
                        FontsResources fontsResources,
                        AppConstants appConstants,
                        PlaceManager placeManager,
                        ToolbarPresenter toolbarPresenter,
                        UniversalAnalytics universalAnalytics) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN);

        this.uiFactory = uiFactory;
        this.placeManager = placeManager;
        this.entityListPresenter = entityListPresenter;
        this.sidebarPresenter = sidebarPresenter;
        this.fontsResources = fontsResources;
        this.myConstants = appConstants;
        this.toolbarPresenter = toolbarPresenter;
        this.universalAnalytics = universalAnalytics;

        edit = createEditButton();
        delete = createDeleteButton();
        deselect = createDeselectButton();

        edit.setEnabled(false);
        delete.setEnabled(false);
        deselect.setEnabled(false);
    }

    @Override
    public void setStateFromPlaceRequest(SetStateFromPlaceRequestEvent event) {
        PlaceRequest placeRequest = event.getPlaceRequest();

        String kindFromPlaceRequest = placeRequest.getParameter(UrlParameters.KIND, "");
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

        if (event.isOpen()) {
            universalAnalytics.sendEvent(UI_ELEMENTS, "close").eventLabel("Visualizer -> Actions Sidebar");
        } else {
            universalAnalytics.sendEvent(UI_ELEMENTS, "open").eventLabel("Visualizer -> Actions Sidebar");
        }
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
    public void onEntitiesSelected(EntitiesSelectedEvent event) {
        currentParsedEntities = event.getParsedEntities();
        currentParsedEntity = null;
        getView().collapseEntityDetails();
        enableContextualMenu();
    }

    @Override
    protected void onBind() {
        super.onBind();

        setInSlot(SLOT_ENTITIES, entityListPresenter);
        setInSlot(SLOT_TOOLBAR, toolbarPresenter);
        setInSlot(SLOT_KINDS, sidebarPresenter);

        toolbarPresenter.setButtons(Lists.newArrayList(edit, deselect, delete));
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
        addRegisteredHandler(EntitiesSelectedEvent.getType(), this);

        addVisibleHandler(ToolbarToggleEvent.getType(), this);
    }

    private ToolbarButton createDeleteButton() {
        return uiFactory.createToolbarButton(myConstants.delete(), fontsResources.icons().icon_delete(),
                new ToolbarButtonCallback() {
                    @Override
                    public void onClicked() {
                        delete();

                        universalAnalytics.sendEvent(UI_ELEMENTS, "click").eventLabel("Visualizer -> Delete Entity");
                    }
                }, DebugIds.DELETE_ENGAGE
        );
    }

    private void delete() {
        if (currentParsedEntity != null) {
            DeleteEntityEvent.fire(this, currentParsedEntity);
        } else if (!currentParsedEntities.isEmpty()) {
            DeleteEntitiesEvent.fire(this, DeleteEntities.SET, currentParsedEntities);
        }
    }

    private ToolbarButton createEditButton() {
        return uiFactory.createToolbarButton(myConstants.edit(), fontsResources.icons().icon_edit(),
                new ToolbarButtonCallback() {
                    @Override
                    public void onClicked() {
                        edit();

                        universalAnalytics.sendEvent(UI_ELEMENTS, "click").eventLabel("Visualizer -> Edit Entity");
                    }
                }, DebugIds.EDIT
        );
    }

    private void edit() {
        if (currentParsedEntity != null) {
            EntityDto entityDto = currentParsedEntity.getEntityDto();
            KeyDto keyDto = entityDto.getKey();

            PlaceRequest.Builder builder = new PlaceRequest.Builder().nameToken(NameTokens.editEntity)
                    .with(UrlParameters.KIND, keyDto.getKind())
                    .with(UrlParameters.KEY, keyDto.getEncodedKey());

            placeManager.revealPlace(builder.build());
        } else if (currentParsedEntities != null && !currentParsedEntities.isEmpty()) {
            EditEntitiesEvent.fire(this, currentParsedEntities);
        }
    }

    private ToolbarButton createDeselectButton() {
        return uiFactory.createToolbarButton(myConstants.deselect(), fontsResources.icons().icon_deselect(),
                new ToolbarButtonCallback() {
                    @Override
                    public void onClicked() {
                        deselect();
                    }
                }, DebugIds.EDIT
        );
    }

    private void deselect() {
        universalAnalytics.sendEvent(UI_ELEMENTS, "click").eventLabel("Visualizer -> Deselect Entity");

        DeselectEvent.fire(this);
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
        deselect.setEnabled(true);
    }

    private void disableContextualMenu() {
        edit.setEnabled(false);
        delete.setEnabled(false);
        deselect.setEnabled(false);
    }
}
