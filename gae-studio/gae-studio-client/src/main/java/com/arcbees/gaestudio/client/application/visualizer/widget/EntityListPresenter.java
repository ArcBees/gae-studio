/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.arcbees.gaestudio.client.application.event.RowLockedEvent;
import com.arcbees.gaestudio.client.application.event.RowUnlockedEvent;
import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.application.visualizer.event.DeleteEntitiesEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitiesDeletedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitiesSelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntityDeletedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntityPageLoadedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySavedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.KindSelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.SetStateFromPlaceRequestEvent;
import com.arcbees.gaestudio.client.application.visualizer.widget.namespace.DeleteFromNamespaceHandler;
import com.arcbees.gaestudio.client.application.visualizer.widget.namespace.NamespacesListPresenter;
import com.arcbees.gaestudio.client.application.visualizer.widget.namespace.NamespacesListPresenterFactory;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.place.ParameterTokens;
import com.arcbees.gaestudio.client.rest.EntitiesService;
import com.arcbees.gaestudio.client.rest.ExportService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.DeleteEntities;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.shared.RestDispatch;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

import static com.arcbees.gaestudio.client.application.visualizer.event.EntitiesDeletedEvent.EntitiesDeletedHandler;
import static com.arcbees.gaestudio.client.application.visualizer.event.EntityDeletedEvent.EntityDeletedHandler;
import static com.arcbees.gaestudio.client.application.visualizer.event.EntitySavedEvent.EntitySavedHandler;
import static com.arcbees.gaestudio.client.place.ParameterTokens.APP_ID;
import static com.arcbees.gaestudio.client.place.ParameterTokens.ID;
import static com.arcbees.gaestudio.client.place.ParameterTokens.KIND;
import static com.arcbees.gaestudio.client.place.ParameterTokens.NAME;
import static com.arcbees.gaestudio.client.place.ParameterTokens.NAMESPACE;
import static com.arcbees.gaestudio.client.place.ParameterTokens.PARENT_ID;
import static com.arcbees.gaestudio.client.place.ParameterTokens.PARENT_KIND;

public class EntityListPresenter extends PresenterWidget<EntityListPresenter.MyView>
        implements EntityListUiHandlers, EntitySavedHandler, EntityDeletedHandler, EntitiesDeletedHandler,
        DeleteFromNamespaceHandler, SetStateFromPlaceRequestEvent.SetStateFromPlaceRequestHandler,
        KindSelectedEvent.KindSelectedHandler {
    interface MyView extends View, HasUiHandlers<EntityListUiHandlers> {
        void setNewKind(String currentKind);

        void setTableDataProvider(AsyncDataProvider<ParsedEntity> dataProvider);

        void setRowCount(Integer count);

        void setData(Range range, List<ParsedEntity> parsedEntities);

        void addOrReplaceEntity(EntityDto parsedEntity);

        void hideList();

        void removeEntity(EntityDto entityDTO);

        void addProperty(String propertyName);

        void redraw();

        void removeKindSpecificColumns();

        void unselectRows();

        void setRowSelected(String idString);
    }

    public static final Object SLOT_NAMESPACES = new Object();

    private final RestDispatch restDispatch;
    private final EntitiesService entitiesService;
    private final PlaceManager placeManager;
    private final PropertyNamesAggregator propertyNamesAggregator;
    private final NamespacesListPresenter namespacesListPresenter;
    private final ExportService exportService;

    private String currentKind;

    @Inject
    EntityListPresenter(EventBus eventBus,
                        MyView view,
                        PlaceManager placeManager,
                        RestDispatch restDispatch,
                        EntitiesService entitiesService,
                        PropertyNamesAggregator propertyNamesAggregator,
                        NamespacesListPresenterFactory namespacesListPresenterFactory,
                        ExportService exportService) {
        super(eventBus, view);

        this.placeManager = placeManager;
        this.restDispatch = restDispatch;
        this.entitiesService = entitiesService;
        this.propertyNamesAggregator = propertyNamesAggregator;
        this.namespacesListPresenter = namespacesListPresenterFactory.create(this);
        this.exportService = exportService;

        getView().setUiHandlers(this);

        setTableDataProvider();
    }

    @Override
    public void onKindSelected(KindSelectedEvent event) {
        getView().unselectRows();
    }

    @Override
    public void onEntitySelected(Set<ParsedEntity> selectedEntities) {
        RowLockedEvent.fire(this);
        if (selectedEntities.size() == 1) {
            ParsedEntity selectedEntity = Iterables.getOnlyElement(selectedEntities);
            EntitySelectedEvent.fire(this, selectedEntity);
            revealEntityPlace(selectedEntity);
        } else {
            EntitiesSelectedEvent.fire(this, selectedEntities);
        }
    }

    @Override
    public void onRowUnlock() {
        RowUnlockedEvent.fire(this);
    }

    @Override
    public void refresh() {
        KindSelectedEvent.fire(this, currentKind);
    }

    @Override
    public void onExport(Collection<ParsedEntity> selectedEntities) {
        Collection<String> entitiesEncodedKeys = Collections2.transform(selectedEntities, new Function<ParsedEntity, String>() {
            @Override
            public String apply(ParsedEntity parsedEntity) {
                return parsedEntity.getKey().getEncodedString();
            }
        });

        String url = exportService.getExportSelectedEntities(currentKind, entitiesEncodedKeys);
    }

    public void loadKind(String kind) {
        this.currentKind = kind;

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
        loadKind(this.currentKind);
    }

    @Override
    public void onDeleteAllFromNamespace(AppIdNamespaceDto namespaceDto) {
        if (!Strings.isNullOrEmpty(currentKind)) {
            if (namespaceDto == null) {
                DeleteEntitiesEvent.fire(this, DeleteEntities.KIND, currentKind);
            } else {
                DeleteEntitiesEvent.fire(this, DeleteEntities.KIND_NAMESPACE, currentKind, namespaceDto.getNamespace());
            }
        }
    }

    @Override
    public void setStateFromPlaceRequest(SetStateFromPlaceRequestEvent event) {
        PlaceRequest placeRequest = event.getPlaceRequest();
        String idString = placeRequest.getParameter(ParameterTokens.ID, "");

        if (!Strings.isNullOrEmpty(idString)) {
            getView().setRowSelected(idString);
            RowLockedEvent.fire(this);
        }
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(EntitySavedEvent.getType(), this);
        addRegisteredHandler(EntityDeletedEvent.getType(), this);
        addRegisteredHandler(EntitiesDeletedEvent.getType(), this);
        addRegisteredHandler(SetStateFromPlaceRequestEvent.getType(), this);
        addRegisteredHandler(KindSelectedEvent.getType(), this);

        setInSlot(SLOT_NAMESPACES, namespacesListPresenter);
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
        restDispatch.execute(entitiesService.getCountByKind(currentKind), new AsyncCallbackImpl<Integer>() {
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
            restDispatch.execute(entitiesService.getByKind(currentKind, range.getStart(), range.getLength()),
                    new AsyncCallbackImpl<List<EntityDto>>() {
                        @Override
                        public void onSuccess(List<EntityDto> result) {
                            onLoadPageSuccess(result, display);
                        }
                    }
            );
        }
        EntityPageLoadedEvent.fire(this);
    }

    private void onLoadPageSuccess(List<EntityDto> entities, HasData<ParsedEntity> display) {
        List<ParsedEntity> parsedEntities = new ArrayList<ParsedEntity>();

        for (EntityDto entityDto : entities) {
            ParsedEntity parsedEntity = new ParsedEntity(entityDto);
            parsedEntities.add(parsedEntity);
        }

        adjustColumns(parsedEntities);
        getView().setData(display.getVisibleRange(), parsedEntities);
        setTotalCount();
    }

    private void adjustColumns(List<ParsedEntity> entities) {
        getView().removeKindSpecificColumns();

        Set<String> propertyNames = propertyNamesAggregator.aggregatePropertyNames(entities);

        for (String propertyName : propertyNames) {
            getView().addProperty(propertyName);
        }

        getView().redraw();
    }

    private void revealEntityPlace(ParsedEntity parsedEntity) {
        EntityDto entityDto = parsedEntity.getEntityDto();
        KeyDto keyDto = entityDto.getKey();

        PlaceRequest.Builder builder = new PlaceRequest.Builder().nameToken(NameTokens.entity)
                .with(KIND, keyDto.getKind())
                .with(ID, Long.toString(keyDto.getId()))
                .with(NAME, keyDto.getName())
                .with(NAMESPACE, keyDto.getAppIdNamespace().getNamespace())
                .with(APP_ID, keyDto.getAppIdNamespace().getAppId());

        if (keyDto.getParentKey() != null) {
            builder = builder.with(PARENT_KIND, keyDto.getParentKey().getKind())
                    .with(PARENT_ID, Long.toString(keyDto.getParentKey().getId()));
        }

        placeManager.revealPlace(builder.build());
    }
}
