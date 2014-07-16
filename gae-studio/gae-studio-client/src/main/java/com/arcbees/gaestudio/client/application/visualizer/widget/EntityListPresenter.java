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

import com.arcbees.analytics.client.universalanalytics.UniversalAnalytics;
import com.arcbees.gaestudio.client.application.analytics.EventCategories;
import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.event.RowLockedEvent;
import com.arcbees.gaestudio.client.application.event.RowUnlockedEvent;
import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.application.visualizer.event.DeleteEntitiesEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitiesDeletedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitiesSavedEvent;
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
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.resources.AppMessages;
import com.arcbees.gaestudio.client.rest.EntitiesService;
import com.arcbees.gaestudio.client.rest.GqlService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.client.util.RestCallbackImpl;
import com.arcbees.gaestudio.shared.DeleteEntities;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
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

import static com.arcbees.gaestudio.client.application.analytics.EventCategories.UI_ELEMENTS;
import static com.arcbees.gaestudio.client.application.visualizer.event.EntitiesDeletedEvent.EntitiesDeletedHandler;
import static com.arcbees.gaestudio.client.application.visualizer.event.EntityDeletedEvent.EntityDeletedHandler;
import static com.arcbees.gaestudio.client.application.visualizer.event.EntitySavedEvent.EntitySavedHandler;

public class EntityListPresenter extends PresenterWidget<EntityListPresenter.MyView>
        implements EntityListUiHandlers, EntitySavedHandler, EntityDeletedHandler, EntitiesDeletedHandler,
        DeleteFromNamespaceHandler, SetStateFromPlaceRequestEvent.SetStateFromPlaceRequestHandler,
        KindSelectedEvent.KindSelectedHandler, EntitiesSavedEvent.EntitiesSavedHandler {
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

        void setRowSelected(String encodedKey);
    }

    public static final Object SLOT_NAMESPACES = new Object();

    private final RestDispatch restDispatch;
    private final EntitiesService entitiesService;
    private final PlaceManager placeManager;
    private final PropertyNamesAggregator propertyNamesAggregator;
    private final NamespacesListPresenter namespacesListPresenter;
    private final GqlService gqlService;
    private final AppConstants appConstants;
    private final AppMessages appMessages;
    private final UniversalAnalytics universalAnalytics;

    private String currentKind;

    @Inject
    EntityListPresenter(EventBus eventBus,
                        MyView view,
                        PlaceManager placeManager,
                        RestDispatch restDispatch,
                        EntitiesService entitiesService,
                        PropertyNamesAggregator propertyNamesAggregator,
                        NamespacesListPresenterFactory namespacesListPresenterFactory,
                        GqlService gqlService,
                        AppConstants appConstants,
                        AppMessages appMessages,
                        UniversalAnalytics universalAnalytics) {
        super(eventBus, view);

        this.placeManager = placeManager;
        this.restDispatch = restDispatch;
        this.entitiesService = entitiesService;
        this.propertyNamesAggregator = propertyNamesAggregator;
        this.gqlService = gqlService;
        this.appConstants = appConstants;
        this.appMessages = appMessages;
        this.universalAnalytics = universalAnalytics;
        this.namespacesListPresenter = namespacesListPresenterFactory.create(this);

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
    public void onEntitiesSaved(EntitiesSavedEvent event) {
        refresh();
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

        universalAnalytics.sendEvent(UI_ELEMENTS, "click")
                .eventLabel("Visualizer -> List View -> Delete All Entities Button");
    }

    @Override
    public void setStateFromPlaceRequest(SetStateFromPlaceRequestEvent event) {
        PlaceRequest placeRequest = event.getPlaceRequest();
        String encodedKey = placeRequest.getParameter(UrlParameters.KEY, "");

        if (!Strings.isNullOrEmpty(encodedKey)) {
            getView().setRowSelected(encodedKey);
            RowLockedEvent.fire(this);
        }
    }

    @Override
    public void runGqlQuery(String gqlRequest) {
        if (requestHasNoSelect(gqlRequest)) {
            DisplayMessageEvent.fire(this, new Message(appConstants.missingSelectInRequest(), MessageStyle.ERROR));

            return;
        }

        restDispatch.execute(gqlService.executeGqlRequest(gqlRequest), new RestCallbackImpl<List<EntityDto>>() {
            @Override
            public void onSuccess(List<EntityDto> entities) {
                if (entities.isEmpty()) {
                    DisplayMessageEvent.fire(this, new Message(appConstants.noEntitiesMatchRequest(),
                            MessageStyle.ERROR));
                } else {
                    showEntities(entities);

                    DisplayMessageEvent.fire(this, new Message(appMessages.entitiesMatchRequest(entities.size()),
                            MessageStyle.SUCCESS));
                }
            }

            @Override
            public void setResponse(Response response) {
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
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(EntitySavedEvent.getType(), this);
        addRegisteredHandler(EntitiesSavedEvent.getType(), this);
        addRegisteredHandler(EntityDeletedEvent.getType(), this);
        addRegisteredHandler(EntitiesDeletedEvent.getType(), this);
        addRegisteredHandler(SetStateFromPlaceRequestEvent.getType(), this);
        addRegisteredHandler(KindSelectedEvent.getType(), this);

        namespacesListPresenter.addValueChangeHandler(new ValueChangeHandler<AppIdNamespaceDto>() {
            @Override
            public void onValueChange(ValueChangeEvent<AppIdNamespaceDto> event) {
                universalAnalytics.sendEvent(EventCategories.UI_ELEMENTS, "value changed")
                        .eventLabel("Visualizer -> List View -> Kinds");
            }
        });

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
        List<ParsedEntity> parsedEntities = new ArrayList<>();

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
                .with(UrlParameters.KIND, keyDto.getKind())
                .with(UrlParameters.KEY, keyDto.getEncodedKey());

        placeManager.revealPlace(builder.build());
    }

    private void showEntities(List<EntityDto> entities) {
        String text = "";
        for (EntityDto entityDto : entities) {
            text += entityDto.getKey().getKind() + " ";
            text += entityDto.getKey().getId() + "\n";
        }

        Window.alert(text);
    }

    private boolean requestHasNoSelect(String gqlRequest) {
        return !gqlRequest.trim().toUpperCase().startsWith("SELECT");
    }
}
