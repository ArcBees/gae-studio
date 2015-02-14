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
import java.util.List;
import java.util.Set;

import com.arcbees.gaestudio.client.application.event.ColumnVisibilityChangedEvent;
import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.event.RowLockedEvent;
import com.arcbees.gaestudio.client.application.event.RowUnlockedEvent;
import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.application.visualizer.columnfilter.ColumnFilterPresenter;
import com.arcbees.gaestudio.client.application.visualizer.columnfilter.TypeInfoLoadedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.DeselectEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitiesDeletedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitiesSavedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitiesSelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntityDeletedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntityPageLoadedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySavedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.KindSelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.NamespaceSelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.SetStateFromPlaceRequestEvent;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.resources.AppMessages;
import com.arcbees.gaestudio.client.rest.EntitiesService;
import com.arcbees.gaestudio.client.rest.GqlService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.client.util.RestCallbackImpl;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gwt.http.client.Response;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.client.RestDispatch;
import com.gwtplatform.dispatch.rest.shared.RestAction;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

import static com.arcbees.gaestudio.client.application.visualizer.event.EntitiesDeletedEvent.EntitiesDeletedHandler;
import static com.arcbees.gaestudio.client.application.visualizer.event.EntityDeletedEvent.EntityDeletedHandler;
import static com.arcbees.gaestudio.client.application.visualizer.event.EntitySavedEvent.EntitySavedHandler;

public class EntityListPresenter extends PresenterWidget<EntityListPresenter.MyView>
        implements EntityListUiHandlers, EntitySavedHandler, EntityDeletedHandler, EntitiesDeletedHandler,
        SetStateFromPlaceRequestEvent.SetStateFromPlaceRequestHandler, KindSelectedEvent.KindSelectedHandler,
        EntitiesSavedEvent.EntitiesSavedHandler, DeselectEvent.DeselectHandler,
        NamespaceSelectedEvent.NamespaceSelectedHandler, ColumnVisibilityChangedEvent.ColumnVisibilityChangedHandler {
    interface MyView extends View, HasUiHandlers<EntityListUiHandlers> {
        void setNewKind(String currentKind);

        void setTableDataProvider(AsyncDataProvider<ParsedEntity> dataProvider);

        void setRowCount(Integer count);

        void setData(Range range, List<ParsedEntity> parsedEntities);

        void hideList();

        void removeEntity(EntityDto entityDTO);

        void addProperty(String propertyName);

        void redraw();

        void removeKindSpecificColumns();

        void unselectRows();

        void setRowSelected(String encodedKey);

        void blockSendingNewRequests();

        void allowSendingNewRequests();

        void addOrReplaceEntities(List<EntityDto> entities);

        void setKind(String appId, String namespace, String kind);

        void updateColumnVisibility();
    }

    static final Object SLOT_COLUMN_CONFIG_TOOLTIP = new Object();

    private final RestDispatch restDispatch;
    private final EntitiesService entitiesService;
    private final PlaceManager placeManager;
    private final PropertyNamesAggregator propertyNamesAggregator;
    private final GqlService gqlService;
    private final AppConstants appConstants;
    private final AppMessages appMessages;
    private final ColumnFilterPresenter columnFilterPresenter;

    private String currentKind;
    private String currentNamespace;
    private String currentGqlRequest;
    private Integer currentGqlNumberOfEntities = 0;
    private boolean isByGql;

    @Inject
    EntityListPresenter(EventBus eventBus,
            MyView view,
            PlaceManager placeManager,
            RestDispatch restDispatch,
            EntitiesService entitiesService,
            PropertyNamesAggregator propertyNamesAggregator,
            GqlService gqlService,
            AppConstants appConstants,
            AppMessages appMessages,
            ColumnFilterPresenter columnFilterPresenter) {
        super(eventBus, view);

        this.placeManager = placeManager;
        this.restDispatch = restDispatch;
        this.entitiesService = entitiesService;
        this.propertyNamesAggregator = propertyNamesAggregator;
        this.gqlService = gqlService;
        this.appConstants = appConstants;
        this.appMessages = appMessages;
        this.columnFilterPresenter = columnFilterPresenter;

        getView().setUiHandlers(this);

        setTableDataProvider();
    }

    @Override
    public void onKindSelected(KindSelectedEvent event) {
        getView().unselectRows();
        currentGqlRequest = "";
    }

    @Override
    public void onEntitySelected(Set<ParsedEntity> selectedEntities) {
        if (selectedEntities.size() == 1) {
            ParsedEntity selectedEntity = Iterables.getOnlyElement(selectedEntities);
            EntitySelectedEvent.fire(this, selectedEntity);
        } else {
            EntitiesSelectedEvent.fire(this, selectedEntities);
        }
        revealEntityPlace(selectedEntities);
    }

    @Override
    public void onRowUnlock() {
        PlaceRequest placeRequest = new PlaceRequest.Builder(placeManager.getCurrentPlaceRequest())
                .nameToken(NameTokens.visualizer)
                .without(UrlParameters.KEY)
                .build();
        placeManager.revealPlace(placeRequest);

        RowUnlockedEvent.fire(this);
    }

    @Override
    public void refresh() {
        revealEntityPlace(Sets.<ParsedEntity>newHashSet());
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
        getView().addOrReplaceEntities(Lists.newArrayList(event.getEntityDto()));
    }

    @Override
    public void onEntitiesSaved(EntitiesSavedEvent event) {
        getView().addOrReplaceEntities(event.getEntities());
    }

    @Override
    public void onEntityDeleted(EntityDeletedEvent event) {
        getView().removeEntity(event.getEntityDto());
    }

    @Override
    public void onEntitiesDeleted(EntitiesDeletedEvent event) {
        loadKind(this.currentKind);
    }

    @Override
    public void setStateFromPlaceRequest(SetStateFromPlaceRequestEvent event) {
        setRowsSelectedFromPlaceRequest(event);
    }

    @Override
    public void onDeselectEntities(DeselectEvent event) {
        getView().unselectRows();
    }

    @Override
    public void runGqlQuery(String gqlRequest) {
        if (requestHasNoSelect(gqlRequest)) {
            DisplayMessageEvent.fire(this, new Message(appConstants.missingSelectInRequest(), MessageStyle.ERROR));

            return;
        }

        getView().blockSendingNewRequests();

        currentGqlRequest = replaceQuotes(gqlRequest);

        restDispatch.execute(gqlService.getRequestCount(currentGqlRequest), new RestCallbackImpl<Integer>() {
            @Override
            public void onSuccess(Integer number) {
                if (number == 0) {
                    DisplayMessageEvent.fire(this,
                            new Message(appConstants.noEntitiesMatchRequest(), MessageStyle.ERROR));

                    showEntities(new ArrayList<EntityDto>(), new Range(0, 0), number);
                } else {
                    DisplayMessageEvent.fire(this, new Message(appMessages.entitiesMatchRequest(number),
                            MessageStyle.SUCCESS));
                    retrieveEntities(new Range(0, 25), number);
                    currentGqlNumberOfEntities = number;
                }
            }

            @Override
            public void setResponse(Response response) {
                getView().allowSendingNewRequests();

                int statusCode = response.getStatusCode();

                if (statusCode == Response.SC_BAD_REQUEST) {
                    DisplayMessageEvent.fire(this, new Message(appConstants.wrongGqlRequest(), MessageStyle.ERROR));
                } else if (statusCode != Response.SC_OK) {
                    DisplayMessageEvent.fire(this, new Message(appConstants.somethingWentWrong(), MessageStyle.ERROR));
                }
            }
        });
    }

    @Override
    public void setUseGql(boolean isByGql) {
        this.isByGql = isByGql;
    }

    @Override
    public void onNamespaceSelected(NamespaceSelectedEvent event) {
        currentNamespace = event.getNamespace();
    }

    @Override
    public void onColumnVisibilityChanged(ColumnVisibilityChangedEvent event) {
        getView().updateColumnVisibility();
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(EntitySavedEvent.getType(), this);
        addRegisteredHandler(EntitiesSavedEvent.getType(), this);
        addRegisteredHandler(EntityDeletedEvent.getType(), this);
        addRegisteredHandler(EntitiesDeletedEvent.getType(), this);
        addRegisteredHandler(SetStateFromPlaceRequestEvent.getType(), this);
        addRegisteredHandler(KindSelectedEvent.getType(), this);
        addRegisteredHandler(DeselectEvent.getType(), this);
        addRegisteredHandler(NamespaceSelectedEvent.getType(), this);
        addRegisteredHandler(ColumnVisibilityChangedEvent.getType(), this);

        setInSlot(SLOT_COLUMN_CONFIG_TOOLTIP, columnFilterPresenter);
    }

    private void showEntities(List<EntityDto> entities, Range range, Integer number) {
        setData(entities, range);
        getView().setRowCount(number);
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
        restDispatch.execute(getCountByKindAction(), new AsyncCallbackImpl<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                getView().setRowCount(result);
            }
        });
    }

    private RestAction<Integer> getCountByKindAction() {
        if (currentNamespace == null) {
            return entitiesService.getCountByKind(currentKind);
        } else {
            return entitiesService.getCountByKind(currentKind, currentNamespace);
        }
    }

    private void loadPage(final HasData<ParsedEntity> display) {
        if (currentKind == null) {
            display.setRowCount(0);
        } else {
            revealEntityPlace(Sets.<ParsedEntity>newHashSet());

            final Range range = display.getVisibleRange();

            if (isByGql) {
                retrieveEntities(range, currentGqlNumberOfEntities);
            } else {
                restDispatch.execute(getByKindAction(range), new AsyncCallbackImpl<List<EntityDto>>() {
                            @Override
                            public void onSuccess(List<EntityDto> result) {
                                setData(result, range);

                                setTotalCount();
                            }
                        }
                );
            }
        }
        EntityPageLoadedEvent.fire(this);
    }

    private RestAction<List<EntityDto>> getByKindAction(Range range) {
        if (currentNamespace == null) {
            return entitiesService.getByKind(currentKind, range.getStart(), range.getLength());
        } else {
            return entitiesService.getByKind(currentKind, currentNamespace, range.getStart(), range.getLength());
        }
    }

    private void setData(List<EntityDto> entities, Range range) {
        List<ParsedEntity> parsedEntities = transformToParsedEntities(entities);

        adjustColumns(parsedEntities);
        getView().setData(range, parsedEntities);
    }

    private void adjustColumns(List<ParsedEntity> entities) {
        getView().removeKindSpecificColumns();

        if (!entities.isEmpty()) {
            Set<String> propertyNames = propertyNamesAggregator.aggregatePropertyNames(entities);

            for (String propertyName : propertyNames) {
                getView().addProperty(propertyName);
            }

            List<String> columnNames = Lists.newArrayList(ParsedEntityColumnCreator.DEFAULT_COLUMN_NAMES);
            columnNames.addAll(propertyNames);
            ParsedEntity prototype = entities.get(0);
            TypeInfoLoadedEvent.fire(this, columnNames, prototype);

            AppIdNamespaceDto appIdNamespace = prototype.getKey().getAppIdNamespace();
            getView().setKind(appIdNamespace.getAppId(), appIdNamespace.getNamespace(), prototype.getKey().getKind());
        }

        getView().redraw();
    }

    private void revealEntityPlace(Set<ParsedEntity> parsedEntity) {
        List<String> keys = FluentIterable.from(parsedEntity)
                .transform(new Function<ParsedEntity, String>() {
                    @Override
                    public String apply(ParsedEntity input) {
                        return input.getKey().getEncodedKey();
                    }
                }).toList();

        String keysParam = Joiner.on(",").join(keys);
        PlaceRequest.Builder builder = new PlaceRequest.Builder(placeManager.getCurrentPlaceRequest())
                .nameToken(NameTokens.visualizer);

        if (keys.isEmpty()) {
            RowUnlockedEvent.fire(this);
            builder.without(UrlParameters.KEY);
        } else {
            builder.with(UrlParameters.KEY, keysParam);
        }

        placeManager.revealPlace(builder.build());
    }

    private List<ParsedEntity> transformToParsedEntities(List<EntityDto> entities) {
        List<ParsedEntity> parsedEntities = new ArrayList<>();

        for (EntityDto entityDto : entities) {
            ParsedEntity parsedEntity = new ParsedEntity(entityDto);
            parsedEntities.add(parsedEntity);
        }

        return parsedEntities;
    }

    private boolean requestHasNoSelect(String gqlRequest) {
        return !gqlRequest.trim().toUpperCase().startsWith("SELECT");
    }

    private String replaceQuotes(String gqlRequest) {
        return gqlRequest.replace("\"", "'");
    }

    private void retrieveEntities(final Range range, final Integer numberOfEntities) {
        restDispatch.execute(gqlService.executeGqlRequest(currentGqlRequest,
                        range.getStart(), range.getLength()),
                new AsyncCallbackImpl<List<EntityDto>>() {
                    @Override
                    public void onSuccess(List<EntityDto> result) {
                        showEntities(result, range, numberOfEntities);
                    }
                }
        );
    }

    private void setRowsSelectedFromPlaceRequest(SetStateFromPlaceRequestEvent event) {
        PlaceRequest placeRequest = event.getPlaceRequest();
        String encodedKeysParam = placeRequest.getParameter(UrlParameters.KEY, "");

        if (!Strings.isNullOrEmpty(encodedKeysParam)) {
            List<String> encodedKeys = Splitter.on(",").splitToList(encodedKeysParam);

            if (encodedKeys.size() == 1) {
                RowLockedEvent.fire(this);
            }

            for (String encodedKey : encodedKeys) {
                getView().setRowSelected(encodedKey);
            }
        }
    }
}
