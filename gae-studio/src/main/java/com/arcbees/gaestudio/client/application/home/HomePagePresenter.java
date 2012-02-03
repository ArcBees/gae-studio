package com.arcbees.gaestudio.client.application.home;

import com.arcbees.gaestudio.client.application.ApplicationPresenter;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.shared.dispatch.GenerateSampleDataAction;
import com.arcbees.gaestudio.shared.dispatch.GenerateSampleDataResult;
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

public class HomePagePresenter extends
        Presenter<HomePagePresenter.MyView, HomePagePresenter.MyProxy> {

    public interface MyView extends View {
    }

    @ProxyStandard
    @NameToken(NameTokens.home)
    public interface MyProxy extends ProxyPlace<HomePagePresenter> {
    }
    
    private DispatchAsync dispatcher;

    @Inject
    public HomePagePresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                             final DispatchAsync dispatcher) {
        super(eventBus, view, proxy);
        this.dispatcher = dispatcher;
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, ApplicationPresenter.TYPE_SetMainContent, this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        dispatcher.execute(new GenerateSampleDataAction(),
                new AsyncCallback<GenerateSampleDataResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public void onSuccess(GenerateSampleDataResult result) {
                    }
                });
    }

}
