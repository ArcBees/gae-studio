/**
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.application.visualizer.event.EditEntityEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySavedEvent;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.shared.dispatch.UpdateEntityAction;
import com.arcbees.gaestudio.shared.dispatch.UpdateEntityResult;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class EntityDetailsPresenter extends PresenterWidget<EntityDetailsPresenter.MyView>
        implements EditEntityEvent.EditEntityHandler, EntityDetailsUiHandlers {
    interface MyView extends View, HasUiHandlers<EntityDetailsUiHandlers> {
        void displayEntityDetails(String json);

        void hide();

        void showError(String message);
    }

    private final DispatchAsync dispatcher;
    private ParsedEntity currentParsedEntity;

    @Inject
    EntityDetailsPresenter(EventBus eventBus,
                           MyView view,
                           DispatchAsync dispatcher) {
        super(eventBus, view);

        getView().setUiHandlers(this);

        this.dispatcher = dispatcher;
    }

    @Override
    public void onEditEntity(EditEntityEvent event) {
        currentParsedEntity = event.getParsedEntity();
        getView().displayEntityDetails(currentParsedEntity.getJson());
    }

    @Override
    public void saveEntity(String json) {
        EntityDto entityDTO = currentParsedEntity.getEntityDTO();
        entityDTO.setJson(json);
        dispatcher.execute(new UpdateEntityAction(entityDTO), new AsyncCallback<UpdateEntityResult>() {
            @Override
            public void onFailure(Throwable caught) {
                onSaveEntityFailed(caught);
            }

            @Override
            public void onSuccess(UpdateEntityResult result) {
                onSaveEntitySucceeded(result);
            }
        });
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(EditEntityEvent.getType(), this);
    }

    private void onSaveEntityFailed(Throwable caught) {
        String message = caught.getMessage();
        if (message == null) {
            message = "Unable to save the changes in the datastore.";
        }
        getView().showError(message);
    }

    private void onSaveEntitySucceeded(UpdateEntityResult result) {
        EntityDto newEntityDto = result.getResult();
        EntitySavedEvent.fire(this, newEntityDto);
        Message message = new Message("Entity saved.", MessageStyle.SUCCESS);
        DisplayMessageEvent.fire(this, message);
        getView().hide();
    }
}
