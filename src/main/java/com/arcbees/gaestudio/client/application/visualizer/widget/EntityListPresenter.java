/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySavedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.KindSelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.RefreshEntitiesEvent;
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
import java.util.List;

public class EntityListPresenter extends PresenterWidget<EntityListPresenter.MyView>
        implements KindSelectedEvent.KindSelectedHandler, EntityListUiHandlers, EntitySavedEvent.EntitySavedHandler, RefreshEntitiesEvent.RefreshEntitiesHandler {

    public interface MyView extends View, HasUiHandlers<EntityListUiHandlers> {
        void setNewKind();

        void setTableDataProvider(AsyncDataProvider<ParsedEntity> dataProvider);

        void setRowCount(Integer count);

        void setData(Range range, List<ParsedEntity> parsedEntities);

        void addOrReplaceEntity(EntityDTO parsedEntity);
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
    public void onEntityClicked(ParsedEntity parsedEntity) {
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
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(KindSelectedEvent.getType(), this);
        addRegisteredHandler(EntitySavedEvent.getType(), this);
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

    private void loadPage(final HasData<ParsedEntity> display) {
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

    private void onLoadPageSuccess(GetEntitiesByKindResult result, HasData<ParsedEntity> display) {
        List<ParsedEntity> parsedEntityEntities = new ArrayList<ParsedEntity>();

        for (EntityDTO entityDTO : result.getEntities()) {
            ParsedEntity parsedEntity = new ParsedEntity(entityDTO);
            parsedEntityEntities.add(parsedEntity);
        }

        getView().setData(display.getVisibleRange(), parsedEntityEntities);
    }

}
