/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer;

import com.arcbees.gaestudio.client.application.ApplicationPresenter;
import com.arcbees.gaestudio.client.application.event.RowLockedEvent;
import com.arcbees.gaestudio.client.application.event.RowUnlockedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.KindSelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.sidebar.SidebarPresenter;
import com.arcbees.gaestudio.client.application.visualizer.widget.EntityListPresenter;
import com.arcbees.gaestudio.client.application.visualizer.widget.VisualizerToolbarPresenter;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.google.gwt.event.shared.GwtEvent;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class VisualizerPresenter extends Presenter<VisualizerPresenter.MyView,
        VisualizerPresenter.MyProxy> implements KindSelectedEvent.KindSelectedHandler,
        RowLockedEvent.RowLockedHandler, RowUnlockedEvent.RowUnlockedHandler {
    interface MyView extends View {
        void showEntityDetails();

        void collapseEntityDetails();
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
    private final VisualizerToolbarPresenter visualizerToolbarPresenter;
    private final SidebarPresenter sidebarPresenter;

    @Inject
    VisualizerPresenter(EventBus eventBus,
                        MyView view,
                        MyProxy proxy,
                        EntityListPresenter entityListPresenter,
                        VisualizerToolbarPresenter visualizerToolbarPresenter,
                        SidebarPresenter sidebarPresenter) {
        super(eventBus, view, proxy);

        this.entityListPresenter = entityListPresenter;
        this.visualizerToolbarPresenter = visualizerToolbarPresenter;
        this.sidebarPresenter = sidebarPresenter;
    }

    @Override
    public void onRowLocked(RowLockedEvent rowLockedEvent) {
        getView().showEntityDetails();
    }

    @Override
    public void onRowUnlocked(RowUnlockedEvent rowLockedEvent) {
        getView().collapseEntityDetails();
    }

    @Override
    public void onKindSelected(KindSelectedEvent event) {
        setInSlot(SLOT_ENTITIES, entityListPresenter);

        String kind = event.getKind();

        if (kind.isEmpty()) {
            entityListPresenter.hideList();
        } else {
            entityListPresenter.loadKind(kind);
        }
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, ApplicationPresenter.TYPE_SetMainContent, this);
    }

    @Override
    protected void onBind() {
        super.onBind();

        setInSlot(SLOT_ENTITIES, entityListPresenter);
        setInSlot(SLOT_TOOLBAR, visualizerToolbarPresenter);
        setInSlot(SLOT_KINDS, sidebarPresenter);

        addRegisteredHandler(KindSelectedEvent.getType(), this);
        addRegisteredHandler(RowLockedEvent.getType(), this);
        addRegisteredHandler(RowUnlockedEvent.getType(), this);
    }
}
