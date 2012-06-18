/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.visualizer;

import com.arcbees.gaestudio.client.application.ApplicationPresenter;
import com.arcbees.gaestudio.client.application.visualizer.entitylist.EntityListPresenter;
import com.arcbees.gaestudio.client.application.visualizer.kind.KindListPresenter;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class VisualizerPresenter extends Presenter<VisualizerPresenter.MyView, VisualizerPresenter.MyProxy> {

    public interface MyView extends View {
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.visualizer)
    public interface MyProxy extends ProxyPlace<VisualizerPresenter> {
    }

    public static final Object TYPE_SetKindListPanelContent = new Object();
    public static final Object TYPE_SetEntityListPanelContent = new Object();

    private final KindListPresenter kindListPresenter;
    private final EntityListPresenter entityListPresenter;

    @Inject
    public VisualizerPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                               final KindListPresenter kindListPresenter,
                               final EntityListPresenter entityListPresenter) {
        super(eventBus, view, proxy);

        this.kindListPresenter = kindListPresenter;
        this.entityListPresenter = entityListPresenter;
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, ApplicationPresenter.TYPE_SetMainContent, this);
    }

    @Override
    protected void onBind() {
        super.onBind();

        setInSlot(TYPE_SetKindListPanelContent, kindListPresenter);
        setInSlot(TYPE_SetEntityListPanelContent, entityListPresenter);
    }

}
