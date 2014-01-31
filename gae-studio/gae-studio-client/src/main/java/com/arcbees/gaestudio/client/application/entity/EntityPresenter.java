/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.entity;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.event.FullScreenEvent;
import com.arcbees.gaestudio.client.application.event.RowLockedEvent;
import com.arcbees.gaestudio.client.application.event.RowUnlockedEvent;
import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.application.visualizer.VisualizerPresenter;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.KindSelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.SetStateFromPlaceRequestEvent;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.place.ParameterTokens;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.rest.EntityService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.shared.RestAction;
import com.gwtplatform.dispatch.rest.shared.RestDispatch;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import static com.arcbees.gaestudio.client.place.ParameterTokens.APP_ID;
import static com.arcbees.gaestudio.client.place.ParameterTokens.ID;
import static com.arcbees.gaestudio.client.place.ParameterTokens.KIND;
import static com.arcbees.gaestudio.client.place.ParameterTokens.NAME;
import static com.arcbees.gaestudio.client.place.ParameterTokens.NAMESPACE;
import static com.arcbees.gaestudio.client.place.ParameterTokens.PARENT_ID;
import static com.arcbees.gaestudio.client.place.ParameterTokens.PARENT_KIND;

public class EntityPresenter extends Presenter<EntityPresenter.MyView, EntityPresenter.MyProxy>
        implements EntityUiHandlers, KindSelectedEvent.KindSelectedHandler, RowLockedEvent.RowLockedHandler,
        RowUnlockedEvent.RowUnlockedHandler {
    interface MyView extends View, HasUiHandlers<EntityUiHandlers> {
        void showEntity(EntityDto entityDto);

        void bind();

        void hideEntityDetails();

        void showEntityDetails();
    }

    @ProxyStandard
    @NameToken(NameTokens.entity)
    interface MyProxy extends ProxyPlace<EntityPresenter> {
    }

    private final RestDispatch restDispatch;
    private final EntityService entityService;
    private final AppConstants appConstants;

    @Inject
    EntityPresenter(EventBus eventBus,
                    MyView view,
                    MyProxy proxy,
                    RestDispatch restDispatch,
                    EntityService entityService,
                    AppConstants appConstants) {
        super(eventBus, view, proxy, VisualizerPresenter.SLOT_ENTITY_DETAILS);

        this.restDispatch = restDispatch;
        this.entityService = entityService;
        this.appConstants = appConstants;

        getView().setUiHandlers(this);
    }

    @Override
    public void activateFullScreen() {
        FullScreenEvent.fire(this, true);
    }

    @Override
    public void deactivateFullScreen() {
        FullScreenEvent.fire(this, false);
    }

    @Override
    public void onKindSelected(KindSelectedEvent event) {
    }

    @Override
    public void prepareFromRequest(final PlaceRequest request) {
        super.prepareFromRequest(request);

        if (!isVisible()) {
            Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
                @Override
                public void execute() {
                    SetStateFromPlaceRequestEvent.fire(EntityPresenter.this, request);
                }
            });
        }

        displayEntityFromPlaceRequest(request);
    }

    @Override
    public void onRowLocked(RowLockedEvent rowLockedEvent) {
        getView().showEntityDetails();
    }

    @Override
    public void onRowUnlocked(RowUnlockedEvent rowLockedEvent) {
        getView().hideEntityDetails();
    }

    @Override
    protected void onBind() {
        super.onBind();

        getView().bind();

        addRegisteredHandler(KindSelectedEvent.getType(), this);
        addRegisteredHandler(RowLockedEvent.getType(), this);
        addRegisteredHandler(RowUnlockedEvent.getType(), this);
    }

    private void displayEntityFromPlaceRequest(PlaceRequest request) {
        String kind = request.getParameter(KIND, null);
        String id = request.getParameter(ID, "-1");
        String name = request.getParameter(NAME, null);
        String parentKind = request.getParameter(PARENT_KIND, "");
        String parentId = request.getParameter(PARENT_ID, "");
        String namespace = request.getParameter(NAMESPACE, null);
        String appId = request.getParameter(APP_ID, null);

        String failureMessage = appConstants.failedGettingEntity();

        AsyncCallbackImpl<EntityDto> callback = new AsyncCallbackImpl<EntityDto>(failureMessage) {
            @Override
            public void onSuccess(EntityDto result) {
                displayEntityDto(result);
            }
        };

        RestAction<EntityDto> getEntityAction =
                entityService.getEntity(kind, appId, namespace, parentId, parentKind, name, Long.valueOf(id));

        restDispatch.execute(getEntityAction, callback);
    }

    private void displayEntityDto(EntityDto entityDto) {
        getView().showEntity(entityDto);
    }
}
