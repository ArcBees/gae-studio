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
import com.arcbees.gaestudio.client.place.NameTokens;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

public class HeaderPresenter extends PresenterWidget<HeaderPresenter.MyView> implements HeaderUiHandlers {
    interface MyView extends View, HasUiHandlers<HeaderUiHandlers> {
        void setProfilerActive();
    }

    private final PlaceManager placeManager;
    private final SupportPresenter supportPresenter;

    @Inject
    HeaderPresenter(EventBus eventBus,
                    MyView view,
                    PlaceManager placeManager,
                    SupportPresenter supportPresenter) {
        super(eventBus, view);

        this.placeManager = placeManager;
        this.supportPresenter = supportPresenter;

        getView().setUiHandlers(this);
    }

    @Override
    public void supportClicked() {
        addToPopupSlot(supportPresenter);
    }

    @Override
    protected void onReveal() {
        super.onReset();

        activateCurrentLinks();
    }

    private void activateCurrentLinks() {
        PlaceRequest placeRequest = placeManager.getCurrentPlaceRequest();
        String nameToken = placeRequest.getNameToken();

        if (NameTokens.profiler.equals(nameToken)) {
            getView().setProfilerActive();
        }
    }
}
