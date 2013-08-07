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
import com.arcbees.gaestudio.client.application.visualizer.event.EntitiesDeletedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntityDeletedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntityPageLoadedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySavedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.RefreshEntitiesEvent;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.rest.EntitiesService;
import com.arcbees.gaestudio.client.util.MethodCallbackImpl;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

import static com.arcbees.gaestudio.client.application.visualizer.event.EntitiesDeletedEvent.EntitiesDeletedHandler;
import static com.arcbees.gaestudio.client.application.visualizer.event.EntityDeletedEvent.EntityDeletedHandler;
import static com.arcbees.gaestudio.client.application.visualizer.event.EntitySavedEvent.EntitySavedHandler;
import static com.arcbees.gaestudio.client.application.visualizer.event.RefreshEntitiesEvent.RefreshEntitiesHandler;
import static com.arcbees.gaestudio.client.place.ParameterTokens.APP_ID;
import static com.arcbees.gaestudio.client.place.ParameterTokens.ID;
import static com.arcbees.gaestudio.client.place.ParameterTokens.KIND;
import static com.arcbees.gaestudio.client.place.ParameterTokens.NAMESPACE;
import static com.arcbees.gaestudio.client.place.ParameterTokens.PARENT_ID;
import static com.arcbees.gaestudio.client.place.ParameterTokens.PARENT_KIND;

public class EntityListPresenter extends PresenterWidget<EntityListPresenter.MyView> implements
        EntityListUiHandlers, EntitySavedHandler, RefreshEntitiesHandler, EntityDeletedHandler, EntitiesDeletedHandler {
    interface MyView extends View, HasUiHandlers<EntityListUiHandlers> {
        void setNewKind(String currentKind);

        void setTableDataProvider(AsyncDataProvider<ParsedEntity> dataProvider);

        void setRowCount(Integer count);

        void setData(Range range, List<ParsedEntity> parsedEntities);

        void addOrReplaceEntity(EntityDto parsedEntity);

        void hideList();

        void removeEntity(EntityDto entityDTO);
    }

    private final EntitiesService entitiesService;
    private final PlaceManager placeManager;

    private String currentKind;

    @Inject
    EntityListPresenter(EventBus eventBus,
                        MyView view,
                        PlaceManager placeManager,
                        EntitiesService entitiesService) {
        super(eventBus, view);

        this.placeManager = placeManager;
        this.entitiesService = entitiesService;

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
        getView().addOrReplaceEntity(event.getEntityDto());
    }

    @Override
    public void onEntityDeleted(EntityDeletedEvent event) {
        getView().removeEntity(event.getEntityDTO());
    }

    @Override
    public void onEntitiesDeleted(EntitiesDeletedEvent event) {
        loadKind();
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(EntitySavedEvent.getType(), this);
        addRegisteredHandler(EntityDeletedEvent.getType(), this);
        addRegisteredHandler(EntitiesDeletedEvent.getType(), this);
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
        entitiesService.getCountByKind(currentKind, new MethodCallbackImpl<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                getView().setRowCount(result);
            }
        });
    }

    private void loadPage(final HasData<ParsedEntity> display) {
        if (currentKind == null) {
            display.setRowCount(0);
        } else {
            Range range = display.getVisibleRange();
            entitiesService.getByKind(currentKind, range.getStart(), range.getLength(),
                    new MethodCallbackImpl<List<EntityDto>>() {
                        @Override
                        public void onSuccess(List<EntityDto> result) {
                            onLoadPageSuccess(result, display);
                        }
                    });
        }
        EntityPageLoadedEvent.fire(this);
    }

    private void onLoadPageSuccess(List<EntityDto> entities, HasData<ParsedEntity> display) {
        List<ParsedEntity> parsedEntityEntities = new ArrayList<ParsedEntity>();

        for (EntityDto entityDto : entities) {
            ParsedEntity parsedEntity = new ParsedEntity(entityDto);
            parsedEntityEntities.add(parsedEntity);
        }

        getView().setData(display.getVisibleRange(), parsedEntityEntities);
    }

    private void revealEntityPlace(ParsedEntity parsedEntity) {
        EntityDto entityDto = parsedEntity.getEntityDto();
        KeyDto keyDto = entityDto.getKey();

        PlaceRequest.Builder builder = new PlaceRequest.Builder().nameToken(NameTokens.entity)
                .with(KIND, keyDto.getKind())
                .with(ID, Long.toString(keyDto.getId()))
                .with(NAMESPACE, keyDto.getAppIdNamespace().getNamespace())
                .with(APP_ID, keyDto.getAppIdNamespace().getAppId());

        if (keyDto.getParentKey() != null) {
            builder = builder.with(PARENT_KIND, keyDto.getParentKey().getKind())
                    .with(PARENT_ID, Long.toString(keyDto.getParentKey().getId()));
        }

        placeManager.revealPlace(builder.build());
    }
}
