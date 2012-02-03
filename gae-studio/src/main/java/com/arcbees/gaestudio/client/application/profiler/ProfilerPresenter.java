/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.gaestudio.client.application.ApplicationPresenter;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

import java.util.List;

public class ProfilerPresenter
        extends Presenter<ProfilerPresenter.MyView, ProfilerPresenter.MyProxy> {

    public interface MyView extends View {
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.profiler)
    public interface MyProxy extends ProxyPlace<ProfilerPresenter> {
    }
    
    private DispatchAsync dispatcher;

    @Inject
    public ProfilerPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                             final DispatchAsync dispatcher) {
        super(eventBus, view, proxy);
        this.dispatcher = dispatcher;
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, ApplicationPresenter.TYPE_SetMainContent, this);
    }
    
    @Override
    protected void onBind() {
        super.onBind();
    }

}
