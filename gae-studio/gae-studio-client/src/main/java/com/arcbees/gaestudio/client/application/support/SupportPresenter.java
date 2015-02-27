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

package com.arcbees.gaestudio.client.application.support;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.rest.MailService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.client.RestDispatch;
import com.gwtplatform.dispatch.rest.shared.RestAction;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

public class SupportPresenter extends PresenterWidget<SupportPresenter.MyView> implements SupportUiHandlers {
    interface MyView extends PopupView, HasUiHandlers<SupportUiHandlers> {
        void edit(SupportMessage supportMessage);

        void showViolations(Set<ConstraintViolation<SupportMessage>> constraintViolations);

        void hideViolations();
    }

    private static final String API_KEY = "apikey";

    private final AppConstants appConstants;
    private final RestDispatch dispatch;
    private final MailService mailService;
    private final Validator validator;

    @Inject
    SupportPresenter(
            EventBus eventBus,
            MyView view,
            AppConstants appConstants,
            RestDispatch dispatch,
            MailService mailService,
            Validator validator) {
        super(eventBus, view);

        this.appConstants = appConstants;
        this.dispatch = dispatch;
        this.mailService = mailService;
        this.validator = validator;

        getView().setUiHandlers(this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        getView().edit(new SupportMessage());
        getView().hideViolations();
    }

    @Override
    public void send(SupportMessage supportMessage) {
        Set<ConstraintViolation<SupportMessage>> constraintViolations = validator.validate(supportMessage);

        if (constraintViolations.isEmpty()) {
            getView().hideViolations();
            sendMessage(supportMessage);
        } else {
            getView().showViolations(constraintViolations);
        }
    }

    private void sendMessage(SupportMessage supportMessage) {
        RestAction<Void> action = mailService.sendMessage(MessageRequest.fromSupportMessage(supportMessage), API_KEY);

        dispatch.execute(action, new AsyncCallbackImpl<Void>() {
            @Override
            public void onSuccess(Void result) {
                Message message = new Message(appConstants.thankYou(), MessageStyle.SUCCESS);
                displayMessage(message);
            }
        });
    }

    private void displayMessage(Message message) {
        DisplayMessageEvent.fire(SupportPresenter.this, message);
        getView().hide();
    }
}
