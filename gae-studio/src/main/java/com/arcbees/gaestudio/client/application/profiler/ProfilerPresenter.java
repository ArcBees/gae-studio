/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.gaestudio.client.application.ApplicationPresenter;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class ProfilerPresenter extends Presenter<ProfilerPresenter.MyView, ProfilerPresenter.MyProxy> {

    public interface MyView extends View {
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.profiler)
    public interface MyProxy extends ProxyPlace<ProfilerPresenter> {
    }
    
    public static final Object TYPE_SetRequestPanelContent = new Object();
    public static final Object TYPE_SetStatisticsPanelContent = new Object();
    public static final Object TYPE_SetStatementPanelContent = new Object();
    public static final Object TYPE_SetDetailsPanelContent = new Object();
    
    private final DispatchAsync dispatcher;

    private final RequestPresenter requestPresenter;
    private final StatisticsPresenter statisticsPresenter;
    private final StatementPresenter statementPresenter;
    private final DetailsPresenter detailsPresenter;

    @Inject
    public ProfilerPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                             final DispatchAsync dispatcher, final RequestPresenter requestPresenter,
                             final StatisticsPresenter statisticsPresenter, final StatementPresenter statementPresenter,
                             final DetailsPresenter detailsPresenter) {
        super(eventBus, view, proxy);

        this.dispatcher = dispatcher;

        this.requestPresenter = requestPresenter;
        this.statisticsPresenter = statisticsPresenter;
        this.statementPresenter = statementPresenter;
        this.detailsPresenter = detailsPresenter;
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, ApplicationPresenter.TYPE_SetMainContent, this);
    }
    
    @Override
    protected void onBind() {
        super.onBind();
        
        setInSlot(TYPE_SetRequestPanelContent, requestPresenter);
        setInSlot(TYPE_SetStatisticsPanelContent, statisticsPresenter);
        setInSlot(TYPE_SetStatementPanelContent, statementPresenter);
        setInSlot(TYPE_SetDetailsPanelContent, detailsPresenter);
    }

}
