/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import java.util.ArrayList;
import java.util.List;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.application.visualizer.event.EntityDeletedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntityPageLoadedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySavedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.RefreshEntitiesEvent;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.dispatch.GetEntitiesByKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntitiesByKindResult;
import com.arcbees.gaestudio.shared.dispatch.GetEntityCountByKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityCountByKindResult;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

import static com.arcbees.gaestudio.client.place.ParameterTokens.ID;
import static com.arcbees.gaestudio.client.place.ParameterTokens.KIND;
import static com.arcbees.gaestudio.client.place.ParameterTokens.PARENT_ID;
import static com.arcbees.gaestudio.client.place.ParameterTokens.PARENT_KIND;

public class EntityListPresenter extends PresenterWidget<EntityListPresenter.MyView> implements
        EntityListUiHandlers, EntitySavedEvent.EntitySavedHandler,
        RefreshEntitiesEvent.RefreshEntitiesHandler, EntityDeletedEvent.EntityDeletedHandler {
    interface MyView extends View, HasUiHandlers<EntityListUiHandlers> {
        void setNewKind(String currentKind);

        void setTableDataProvider(AsyncDataProvider<ParsedEntity> dataProvider);

        void setRowCount(Integer count);

        void setData(Range range, List<ParsedEntity> parsedEntities);

        void addOrReplaceEntity(EntityDto parsedEntity);

        void hideList();

        void removeEntity(EntityDto entityDTO);
    }

    private final DispatchAsync dispatcher;
    private final PlaceManager placeManager;

    private String currentKind;

    @Inject
    EntityListPresenter(EventBus eventBus,
                        MyView view,
                        DispatchAsync dispatcher,
                        PlaceManager placeManager) {
        super(eventBus, view);

        this.placeManager = placeManager;
        this.dispatcher = dispatcher;

        getView().setUiHandlers(this);

        setTableDataProvider();
    }

    @Override
    public void onEntitySelected(ParsedEntity parsedEntity) {
        getEventBus().fireEvent(new EntitySelectedEvent(parsedEntity));

        revealEntityPlace(parsedEntity);
    }

    @Override
    public void onRefreshEntities(RefreshEntitiesEvent event) {
        loadKind();
    }

    public void setCurrentKind(String currentKind) {
        this.currentKind = currentKind;
    }

    public void loadKind() {
        setTotalCount();
        getView().setNewKind(currentKind);
    }

    public void hideList() {
        getView().hideList();
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

    private void revealEntityPlace(ParsedEntity parsedEntity) {
        EntityDto entityDto = parsedEntity.getEntityDTO();
        KeyDto keyDto = entityDto.getKey();

        PlaceRequest.Builder builder = new PlaceRequest.Builder().nameToken(NameTokens.entity)
                .with(KIND, keyDto.getKind())
                .with(ID, Long.toString(keyDto.getId()));

        if (keyDto.getParentKey() != null) {
            builder = builder.with(PARENT_KIND, keyDto.getParentKey().getKind())
                    .with(PARENT_ID, Long.toString(keyDto.getParentKey().getId()));
        }

        placeManager.revealPlace(builder.build());
    }
}
