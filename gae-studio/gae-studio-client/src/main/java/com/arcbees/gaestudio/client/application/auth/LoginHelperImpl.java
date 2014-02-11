/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.auth;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.util.CurrentUser;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

public class LoginHelperImpl implements LoginHelper, HasHandlers {
    private final EventBus eventBus;
    private final CurrentUser currentUser;
    private final PlaceManager placeManager;
    private final AppConstants appConstants;

    @Inject
    LoginHelperImpl(EventBus eventBus,
                    CurrentUser currentUser,
                    PlaceManager placeManager,
                    AppConstants appConstants) {
        this.eventBus = eventBus;
        this.currentUser = currentUser;
        this.placeManager = placeManager;
        this.appConstants = appConstants;
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        eventBus.fireEventFromSource(event, this);
    }

    @Override
    public void login() {
        DisplayMessageEvent.fire(this, new Message(appConstants.loggedInSuccessfully(), MessageStyle.SUCCESS));

        currentUser.setLoggedIn(true);

        PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.visualizer).build();
        placeManager.revealPlace(placeRequest);
    }
}
