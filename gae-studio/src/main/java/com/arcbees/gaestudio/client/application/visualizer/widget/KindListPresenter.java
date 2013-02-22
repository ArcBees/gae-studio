/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import java.util.List;

import com.arcbees.gaestudio.client.application.visualizer.event.KindSelectedEvent;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsResult;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class KindListPresenter extends PresenterWidget<KindListPresenter.MyView> implements KindListUiHandlers {
    public interface MyView extends View, HasUiHandlers<KindListUiHandlers> {
        void updateKinds(List<String> kinds);
    }

    private final DispatchAsync dispatcher;

    @Inject
    public KindListPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher) {
        super(eventBus, view);
        
        getView().setUiHandlers(this);

        this.dispatcher = dispatcher;
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        dispatcher.execute(
                new GetEntityKindsAction(),
                new AsyncCallbackImpl<GetEntityKindsResult>() {
                    @Override
                    public void onSuccess(GetEntityKindsResult result) {
                        getView().updateKinds(result.getKinds());
                    }
                });
    }

    @Override
    public void onKindClicked(String kind) {
        KindSelectedEvent.fire(this, kind);
    }
}
