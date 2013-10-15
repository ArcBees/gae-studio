/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.auth;

import com.arcbees.gaestudio.client.application.ApplicationPresenter;
import com.arcbees.gaestudio.client.gatekeeper.LicenseGateKeeper;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

public class AuthPresenter extends Presenter<AuthPresenter.MyView, AuthPresenter.MyProxy> {
    interface MyView extends View {
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.auth)
    @NoGatekeeper
    interface MyProxy extends ProxyPlace<AuthPresenter> {
    }

    private final PlaceManager placeManager;
    private final LicenseGateKeeper licenseGateKeeper;

    @Inject
    AuthPresenter(EventBus eventBus,
                  MyView view,
                  MyProxy proxy,
                  PlaceManager placeManager,
                  LicenseGateKeeper licenseGateKeeper) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.placeManager = placeManager;
        this.licenseGateKeeper = licenseGateKeeper;
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        if (!licenseGateKeeper.canReveal()) {
            PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.licenseExpired).build();
            placeManager.revealPlace(placeRequest);
        } else {
        }
    }
}
