/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.application.visualizer.event.EntityDeletedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntityPageLoadedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySavedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.KindSelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.RefreshEntitiesEvent;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.dispatch.GetEntitiesByKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntitiesByKindResult;
import com.arcbees.gaestudio.shared.dispatch.GetEntityCountByKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityCountByKindResult;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
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
import java.util.List;

public class EntityListPresenter extends PresenterWidget<EntityListPresenter.MyView> implements
        KindSelectedEvent.KindSelectedHandler, EntityListUiHandlers, EntitySavedEvent.EntitySavedHandler,
        RefreshEntitiesEvent.RefreshEntitiesHandler, EntityDeletedEvent.EntityDeletedHandler {
    public interface MyView extends View, HasUiHandlers<EntityListUiHandlers> {
        void setNewKind(String currentKind);

        void setTableDataProvider(AsyncDataProvider<ParsedEntity> dataProvider);

        void setRowCount(Integer count);

        void setData(Range range, List<ParsedEntity> parsedEntities);

        void addOrReplaceEntity(EntityDto parsedEntity);

        void hideList();

        void removeEntity(EntityDto entityDTO);
    }

    private final DispatchAsync dispatcher;
    private String currentKind;

    @Inject
    public EntityListPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher) {
        super(eventBus, view);

        getView().setUiHandlers(this);

        this.dispatcher = dispatcher;

        setTableDataProvider();
    }

    @Override
    public void onKindSelected(KindSelectedEvent event) {
        currentKind = event.getKind();
        if (currentKind.isEmpty()) {
            hideList();
        } else {
            loadKind();
        }
    }

    @Override
    public void onEntitySelected(ParsedEntity parsedEntity) {
        getEventBus().fireEvent(new EntitySelectedEvent(parsedEntity));
    }

    @Override
    public void onRefreshEntities(RefreshEntitiesEvent event) {
        loadKind();
    }

    @Override
    public void onEntitySaved(EntitySavedEvent event) {
        getView().addOrReplaceEntity(event.getEntityDTO());
    }

    @Override
    public void onEntityDeleted(EntityDeletedEvent event) {
        getView().removeEntity(event.getEntityDTO());
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(KindSelectedEvent.getType(), this);
        addRegisteredHandler(EntitySavedEvent.getType(), this);
        addRegisteredHandler(EntityDeletedEvent.getType(), this);
        addRegisteredHandler(RefreshEntitiesEvent.getType(), this);
    }

    private void setTableDataProvider() {
        AsyncDataProvider<ParsedEntity> dataProvider = new AsyncDataProvider<ParsedEntity>() {
            @Override
            protected void onRangeChanged(HasData<ParsedEntity> display) {
                loadPage(display);
            }
        };
        getView().setTableDataProvider(dataProvider);
    }

    private void loadKind() {
        setTotalCount();
        getView().setNewKind(currentKind);
    }

    private void hideList() {
        getView().hideList();
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

    private void loadPage(final HasData<ParsedEntity> display) {
        if (currentKind == null) {
            display.setRowCount(0);
        } else {
            Range range = display.getVisibleRange();
            dispatcher.execute(
                    new GetEntitiesByKindAction.Builder(currentKind).offset(range.getStart()).limit(range.getLength())
                            .build(), new AsyncCallbackImpl<GetEntitiesByKindResult>() {
                        @Override
                        public void onSuccess(GetEntitiesByKindResult result) {
                            onLoadPageSuccess(result, display);
                        }
                    });
        }
        EntityPageLoadedEvent.fire(this);
    }

    private void onLoadPageSuccess(GetEntitiesByKindResult result, HasData<ParsedEntity> display) {
        List<ParsedEntity> parsedEntityEntities = new ArrayList<ParsedEntity>();

        for (EntityDto entityDTO : result.getEntities()) {
            ParsedEntity parsedEntity = new ParsedEntity(entityDTO);
            parsedEntityEntities.add(parsedEntity);
        }

        getView().setData(display.getVisibleRange(), parsedEntityEntities);
    }
}
