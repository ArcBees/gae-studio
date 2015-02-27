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

package com.arcbees.gaestudio.client.application.widget;

import com.arcbees.gaestudio.client.application.support.SupportPresenter;
import com.arcbees.gaestudio.client.application.widget.ajax.LoadingEvent;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
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
    private final SupportPresenter supportPresenter;

    private int loadingEventsAccumulator;

    @Inject
    HeaderPresenter(
            EventBus eventBus,
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
