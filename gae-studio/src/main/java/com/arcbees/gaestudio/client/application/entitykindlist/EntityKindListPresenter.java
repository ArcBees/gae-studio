/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.entitykindlist;

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
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

import java.util.List;

public class EntityKindListPresenter
        extends Presenter<EntityKindListPresenter.MyView, EntityKindListPresenter.MyProxy> {

    public interface MyView extends View {
        void setEntityKinds(List<String> entityKinds);
    }

    @ProxyStandard
    @NameToken(NameTokens.entityKindList)
    public interface MyProxy extends ProxyPlace<EntityKindListPresenter> {
    }
    
    private DispatchAsync dispatcher;

    @Inject
    public EntityKindListPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
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
        dispatcher.execute(new GetEntityKindsAction(0, 100),
                new AsyncCallback<GetEntityKindsResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public void onSuccess(GetEntityKindsResult result) {
                        getView().setEntityKinds(result.getKinds());
                    }
                });
    }

}
