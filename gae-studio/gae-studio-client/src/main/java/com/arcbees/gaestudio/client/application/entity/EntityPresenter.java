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

import org.fusesource.restygwt.client.MethodCallback;

import com.arcbees.gaestudio.client.application.event.FullScreenEvent;
import com.arcbees.gaestudio.client.application.event.RowLockedEvent;
import com.arcbees.gaestudio.client.application.event.RowUnlockedEvent;
import com.arcbees.gaestudio.client.application.visualizer.VisualizerPresenter;
import com.arcbees.gaestudio.client.application.visualizer.event.KindSelectedEvent;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.rest.EntityService;
import com.arcbees.gaestudio.client.util.MethodCallbackImpl;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.google.web.bindery.event.shared.EventBus;
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

public class EntityPresenter extends Presenter<EntityPresenter.MyView, EntityPresenter.MyProxy> implements
        EntityUiHandlers, KindSelectedEvent.KindSelectedHandler, RowLockedEvent.RowLockedHandler,
        RowUnlockedEvent.RowUnlockedHandler {
    interface MyView extends View, HasUiHandlers<EntityUiHandlers> {
        void showEntity(EntityDto entityDto);

        void hideFullscreenButton();

        void bind();

        void showFullscreenButton();

        void hideEntityDetails();

        void showEntityDetails();
    }

    @ProxyStandard
    @NameToken(NameTokens.entity)
    interface MyProxy extends ProxyPlace<EntityPresenter> {
    }

    private final EntityService entityService;
    private final AppConstants appConstants;

    @Inject
    EntityPresenter(EventBus eventBus,
                    MyView view,
                    MyProxy proxy,
                    EntityService entityService,
                    AppConstants appConstants) {
        super(eventBus, view, proxy, VisualizerPresenter.SLOT_ENTITY_DETAILS);

        this.entityService = entityService;
        this.appConstants = appConstants;

        getView().setUiHandlers(this);
    }

    @Override
    public void activateFullScreen() {
        setFullScreen(true);
    }

    @Override
    public void deactivateFullScreen() {
        setFullScreen(false);
    }

    @Override
    public void onKindSelected(KindSelectedEvent event) {
        getView().hideFullscreenButton();
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);

        displayEntityFromPlaceRequest(request);
    }

    @Override
    public void onRowLocked(RowLockedEvent rowLockedEvent) {
        getView().showFullscreenButton();
        getView().showEntityDetails();
    }

    @Override
    public void onRowUnlocked(RowUnlockedEvent rowLockedEvent) {
        getView().hideFullscreenButton();
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

    private void setFullScreen(boolean activate) {
        getEventBus().fireEvent(new FullScreenEvent(activate));
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

        MethodCallback<EntityDto> methodCallback = new MethodCallbackImpl<EntityDto>(failureMessage) {
            @Override
            public void onSuccess(EntityDto result) {
                displayEntityDto(result);
            }
        };

        entityService.getEntity(kind, appId, namespace, parentId, parentKind, name, Long.valueOf(id), methodCallback);
    }

    private void displayEntityDto(EntityDto entityDto) {
        getView().showEntity(entityDto);
    }
}
