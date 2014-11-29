/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.widget;

import com.arcbees.gaestudio.client.application.support.SupportPresenter;
import com.arcbees.gaestudio.client.application.widget.ajax.LoadingEvent;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.rest.AuthService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.client.util.CurrentUser;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.shared.RestAction;
import com.gwtplatform.dispatch.rest.shared.RestDispatch;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

import static com.arcbees.gaestudio.client.application.widget.ajax.LoadingEvent.Action.BEGIN;
import static com.arcbees.gaestudio.client.application.widget.ajax.LoadingEvent.Action.END;

public class HeaderPresenter extends PresenterWidget<HeaderPresenter.MyView>
        implements HeaderUiHandlers, LoadingEvent.LoadingEventHandler {
    interface MyView extends View, HasUiHandlers<HeaderUiHandlers> {
        void setProfilerActive();

        void hideLoadingBar();

        void displayLoadingBar();
    }

    private final PlaceManager placeManager;
    private final AuthService authService;
    private final RestDispatch dispatch;
    private final CurrentUser currentUser;
    private final SupportPresenter supportPresenter;

    private int loadingEventsAccumulator;

    @Inject
    HeaderPresenter(EventBus eventBus,
                    MyView view,
                    PlaceManager placeManager,
                    AuthService authService,
                    RestDispatch dispatch,
                    CurrentUser currentUser,
                    SupportPresenter supportPresenter) {
        super(eventBus, view);

        this.placeManager = placeManager;
        this.authService = authService;
        this.dispatch = dispatch;
        this.currentUser = currentUser;
        this.supportPresenter = supportPresenter;

        getView().setUiHandlers(this);
    }

    @Override
    public void logout() {
        RestAction<Void> action = authService.logout();

        dispatch.execute(action, new AsyncCallbackImpl<Void>() {
            @Override
            public void onSuccess(Void result) {
                currentUser.setLoggedIn(false);

                PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.auth).build();
                placeManager.revealPlace(placeRequest);
            }
        });
    }

    @Override
    public void supportClicked() {
        addToPopupSlot(supportPresenter);
    }

    @Override
    public void onLoadingEvent(LoadingEvent event) {
        if (BEGIN.equals(event.getAction())) {
            loadingEventsAccumulator++;
        } else if (END.equals(event.getAction())) {
            loadingEventsAccumulator--;
        }

        adjustLoadingBar();
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(LoadingEvent.getType(), this);
    }

    @Override
    protected void onReveal() {
        super.onReset();

        activateCurrentLinks();
    }

    private void adjustLoadingBar() {
        if (loadingEventsAccumulator > 0) {
            getView().displayLoadingBar();
        } else {
            getView().hideLoadingBar();
        }
    }

    private void activateCurrentLinks() {
        PlaceRequest placeRequest = placeManager.getCurrentPlaceRequest();
        String nameToken = placeRequest.getNameToken();

        if (NameTokens.profiler.equals(nameToken)) {
            getView().setProfilerActive();
        }
    }
}
