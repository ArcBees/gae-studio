/**
 * Copyright 2015 ArcBees Inc.
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

package com.arcbees.gaestudio.client.application.widget.message;

import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class MessagesPresenter extends PresenterWidget<MessagesPresenter.MyView>
        implements DisplayMessageEvent.DisplayMessageHandler {
    interface MyView extends View {
        void addMessage(Message message);
    }

    @Inject
    MessagesPresenter(
            EventBus eventBus,
            MyView view) {
        super(eventBus, view);
    }

    @Override
    public void onDisplayMessage(DisplayMessageEvent event) {
        getView().addMessage(event.getMessage());
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(DisplayMessageEvent.getType(), this);
    }
}
