/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.visualizer.entitylist;

import com.arcbees.gaestudio.client.application.event.EntitySelectedEvent;
import com.arcbees.gaestudio.client.application.event.KindSelectedEvent;
import com.arcbees.gaestudio.client.domain.EntityJsonParsed;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.dispatch.GetEntitiesByKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntitiesByKindResult;
import com.arcbees.gaestudio.shared.dispatch.GetEntityCountByKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityCountByKindResult;
import com.arcbees.gaestudio.shared.dto.entity.EntityDTO;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntityListPresenter extends PresenterWidget<EntityListPresenter.MyView>
        implements KindSelectedEvent.KindSelectedHandler, EntityListUiHandlers {

    public interface MyView extends View, HasUiHandlers<EntityListUiHandlers> {
        void setNewKind();

        void setTableDataProvider(AsyncDataProvider<EntityJsonParsed> dataProvider);

        void setRowCount(Integer count);

        void setData(Range range, List<EntityJsonParsed> entities, Set<String> properties);
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
        loadKind();
    }

    @Override
    public void onEntityClicked(EntityJsonParsed entityJsonParsed) {
        getEventBus().fireEvent(new EntitySelectedEvent(entityJsonParsed));
    }

    @Override
    public void refreshData() {
        loadKind();
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(KindSelectedEvent.getType(), this);
    }

    private void setTableDataProvider() {
        AsyncDataProvider<EntityJsonParsed> dataProvider = new AsyncDataProvider<EntityJsonParsed>() {
            @Override
            protected void onRangeChanged(HasData<EntityJsonParsed> display) {
                loadPage(display);
            }
        };
        getView().setTableDataProvider(dataProvider);
    }

    private void loadKind() {
        setTotalCount();
        getView().setNewKind();
    }

    private void setTotalCount() {
        dispatcher.execute(new GetEntityCountByKindAction(currentKind),
                new AsyncCallbackImpl<GetEntityCountByKindResult>() {
                    @Override
                    public void onSuccess(GetEntityCountByKindResult result) {
                        getView().setRowCount(result.getCount());
                    }
                });
    }

    private void loadPage(final HasData<EntityJsonParsed> display) {
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
                            onLoadPageSuccess(result, display);
                        }
                    });
        }
    }

    private void onLoadPageSuccess(GetEntitiesByKindResult result, HasData<EntityJsonParsed> display) {
        List<EntityJsonParsed> parsedEntities = new ArrayList<EntityJsonParsed>();

        Set<String> properties = new HashSet<String>();
        for (EntityDTO entityDTO : result.getEntities()) {
            EntityJsonParsed entityJsonParsed = new EntityJsonParsed(entityDTO);
            parsedEntities.add(entityJsonParsed);
            properties.addAll(entityJsonParsed.propertyKeys());
        }

        getView().setData(display.getVisibleRange(), parsedEntities, properties);
    }

}
