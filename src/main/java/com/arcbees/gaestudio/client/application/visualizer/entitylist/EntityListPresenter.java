/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.visualizer.entitylist;

import com.arcbees.gaestudio.client.application.event.EntitySelectedEvent;
import com.arcbees.gaestudio.client.application.event.KindSelectedEvent;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.dispatch.GetEntitiesByKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntitiesByKindResult;
import com.arcbees.gaestudio.shared.dto.entity.EntityDTO;
import com.arcbees.gaestudio.shared.dto.entity.KeyDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import java.util.ArrayList;

public class EntityListPresenter extends PresenterWidget<EntityListPresenter.MyView>
        implements KindSelectedEvent.KindSelectedHandler, EntityListUiHandlers {

    public interface MyView extends View, HasUiHandlers<EntityListUiHandlers> {
        void setNewKind();

        void setTableDataProvider(AsyncDataProvider<EntityDTO> dataProvider);
    }

    private final DispatchAsync dispatcher;
    private String currentKind;

    @Inject
    public EntityListPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher) {
        super(eventBus, view);

        this.dispatcher = dispatcher;

        setTableDataProvider();
    }

    @Override
    public void onKindSelected(KindSelectedEvent event) {
        currentKind = event.getKind();
        getView().setNewKind();
    }

    @Override
    public void onEntityClicked(KeyDTO entityKey, String entityData) {
        getEventBus().fireEvent(new EntitySelectedEvent(entityKey, entityData));
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(KindSelectedEvent.getType(), this);
    }

    private void setTableDataProvider() {
        AsyncDataProvider<EntityDTO> dataProvider = new AsyncDataProvider<EntityDTO>() {
            @Override
            protected void onRangeChanged(HasData<EntityDTO> display) {
                load(display);
            }
        };
        getView().setTableDataProvider(dataProvider);
    }

    private void load(final HasData<EntityDTO> display) {
        if (currentKind == null) {
            display.setRowCount(0);
        } else {
            Range range = display.getVisibleRange();
            dispatcher.execute(
                    new GetEntitiesByKindAction.Builder(currentKind).offset(range.getStart()).limit(range.getLength()
                    ).build(),
                    new AsyncCallbackImpl<GetEntitiesByKindResult>() {
                        @Override
                        public void onSuccess(GetEntitiesByKindResult result) {
                            onGetEntitiesSuccess(result, display);
                        }
                    });
        }
    }

    private void onGetEntitiesSuccess(GetEntitiesByKindResult result, HasData<EntityDTO> display) {
        Range range = display.getVisibleRange();
        ArrayList<EntityDTO> entities = result.getEntities();

        display.setRowCount(range.getStart() + entities.size(), false);
        display.setRowData(range.getStart(), entities);
    }
}
