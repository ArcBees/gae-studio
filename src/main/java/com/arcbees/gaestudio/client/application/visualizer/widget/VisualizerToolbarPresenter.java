/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.gaestudio.client.AppConstants;
import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.application.visualizer.event.EditEntityEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntityDeletedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntityPageLoadedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.KindSelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.RefreshEntitiesEvent;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.shared.dispatch.DeleteEntityAction;
import com.arcbees.gaestudio.shared.dispatch.DeleteEntityResult;
import com.arcbees.gaestudio.shared.dispatch.GetEmptyKindEntityAction;
import com.arcbees.gaestudio.shared.dispatch.GetEmptyKindEntityResult;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class VisualizerToolbarPresenter extends PresenterWidget<VisualizerToolbarPresenter.MyView> implements
        VisualizerToolbarUiHandlers, KindSelectedEvent.KindSelectedHandler, EntitySelectedEvent.EntitySelectedHandler,
        EntityPageLoadedEvent.EntityPageLoadedHandler {

    public interface MyView extends View, HasUiHandlers<VisualizerToolbarUiHandlers> {
        void setKindSelected(boolean isSelected);

        void enableContextualMenu();

        void disableContextualMenu();
    }

    public static final Object TYPE_SetKindsContent = new Object();

    private final DispatchAsync dispatcher;
    private final KindListPresenter kindListPresenter;
    private final AppConstants myConstants;
    private String currentKind = "";
    private ParsedEntity currentParsedEntity;

    @Inject
    public VisualizerToolbarPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher,
                                      final KindListPresenter kindListPresenter, final AppConstants myConstants) {
        super(eventBus, view);
        
        getView().setUiHandlers(this);

        this.dispatcher = dispatcher;
        this.kindListPresenter = kindListPresenter;
        this.myConstants = myConstants;
    }

    @Override
    public void refresh() {
        RefreshEntitiesEvent.fire(this);
    }

    @Override
    public void create() {
        if (!currentKind.isEmpty()) {
            dispatcher.execute(new GetEmptyKindEntityAction(currentKind), new AsyncCallback<GetEmptyKindEntityResult>
                    () {
                @Override
                public void onFailure(Throwable caught) {
                    Message message = new Message(myConstants.errorUnableToGenerateEmptyJson(), MessageStyle.ERROR);
                    DisplayMessageEvent.fire(VisualizerToolbarPresenter.this, message);
                }

                @Override
                public void onSuccess(GetEmptyKindEntityResult result) {
                    EntityDto emptyEntityDto = result.getEntityDTO();
                    EditEntityEvent.fire(VisualizerToolbarPresenter.this, new ParsedEntity(emptyEntityDto));
                }
            });
        }
    }

    @Override
    public void edit() {
        if (currentParsedEntity != null) {
            EditEntityEvent.fire(this, currentParsedEntity);
        }
    }

    @Override
    public void delete() {
        if (currentParsedEntity != null) {
            final EntityDto entityDTO = currentParsedEntity.getEntityDTO();
            dispatcher.execute(new DeleteEntityAction(entityDTO), new AsyncCallback<DeleteEntityResult>() {
                @Override
                public void onSuccess(DeleteEntityResult result) {
                    onEntityDeletedSuccess(entityDTO);
                }

                @Override
                public void onFailure(Throwable caught) {
                    Message message = new Message(myConstants.errorEntityDelete(), MessageStyle.ERROR);
                    DisplayMessageEvent.fire(VisualizerToolbarPresenter.this, message);
                }
            });
        }
    }

    @Override
    public void onKindSelected(KindSelectedEvent event) {
        currentKind = event.getKind();
        getView().setKindSelected(!currentKind.isEmpty());
    }

    @Override
    public void onEntitySelected(EntitySelectedEvent event) {
        currentParsedEntity = event.getParsedEntity();
        getView().enableContextualMenu();
    }

    @Override
    public void onEntityPageLoaded(EntityPageLoadedEvent event) {
        getView().disableContextualMenu();
    }

    @Override
    protected void onBind() {
        super.onBind();

        setInSlot(TYPE_SetKindsContent, kindListPresenter);

        addRegisteredHandler(KindSelectedEvent.getType(), this);
        addRegisteredHandler(EntitySelectedEvent.getType(), this);
        addRegisteredHandler(EntityPageLoadedEvent.getType(), this);
    }

    private void onEntityDeletedSuccess(EntityDto entityDTO) {
        Message message = new Message(myConstants.successEntityDelete(), MessageStyle.SUCCESS);
        DisplayMessageEvent.fire(this, message);
        EntityDeletedEvent.fire(this, entityDTO);
    }

}

