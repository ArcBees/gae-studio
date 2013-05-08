package com.arcbees.gaestudio.client.application.visualizer.sidebar;

import java.util.List;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.visualizer.event.KindSelectedEvent;
import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsResult;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class SidebarPresenter extends PresenterWidget<SidebarPresenter.MyView> implements SidebarUiHandlers {
    public interface MyView extends View, HasUiHandlers<SidebarUiHandlers> {
        void updateKinds(List<String> kinds);

        void addEmptyEntityListStyle();
    }

    private final DispatchAsync dispatcher;

    @Inject
    SidebarPresenter(EventBus eventBus,
                     MyView view,
                     DispatchAsync dispatcher) {
        super(eventBus, view);

        this.dispatcher = dispatcher;

        getView().setUiHandlers(this);
    }

    @Override
    public void displayEntitiesOfSelectedKind(String kind) {
        KindSelectedEvent.fire(this, kind);
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        dispatcher.execute(
                new GetEntityKindsAction(),
                new AsyncCallback<GetEntityKindsResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Failed getting Entity Kinds: " + caught.getMessage());
                    }

                    @Override
                    public void onSuccess(GetEntityKindsResult result) {
                        getView().updateKinds(result.getKinds());
                    }
                });
    }
}
