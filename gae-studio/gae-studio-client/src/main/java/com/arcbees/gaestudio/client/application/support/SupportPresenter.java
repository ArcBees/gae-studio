/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.support;

import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;

import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

public class SupportPresenter extends PresenterWidget<SupportPresenter.MyView> implements SupportUiHandlers {
    interface MyView extends PopupView, HasUiHandlers<SupportUiHandlers> {
        void edit(SupportMessage supportMessage);
    }

    private static final String MAIL_URL = "https://mail.arcbees.com/mail";
    private static final String API_KEY = "apikey";

    private final MessageRequestMapper messageRequestMapper;
    private final AppConstants appConstants;

    @Inject
    SupportPresenter(EventBus eventBus,
                     MyView view,
                     MessageRequestMapper messageRequestMapper,
                     AppConstants appConstants) {
        super(eventBus, view);

        this.messageRequestMapper = messageRequestMapper;
        this.appConstants = appConstants;

        getView().setUiHandlers(this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        getView().edit(new SupportMessage());
    }

    @Override
    public void send(SupportMessage supportMessage) {
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, URL.encode(MAIL_URL));

        requestBuilder.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        requestBuilder.setHeader(HttpHeaders.AUTHORIZATION, API_KEY);

        String requestData = messageRequestMapper.write(MessageRequest.fromSupportMessage(supportMessage));

        try {
            requestBuilder.sendRequest(requestData, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    Message message;

                    if (response.getStatusCode() == Response.SC_NO_CONTENT) {
                        message = new Message(appConstants.thankYou(), MessageStyle.SUCCESS);
                    } else {
                        message = new Message(appConstants.oops(), MessageStyle.ERROR);
                    }

                    displayMessage(message);
                }

                @Override
                public void onError(Request request, Throwable exception) {
                    Message message = new Message(appConstants.oops(), MessageStyle.ERROR);

                    displayMessage(message);
                }
            });
        } catch (RequestException e) {
            Message message = new Message(appConstants.oops(), MessageStyle.ERROR);

            displayMessage(message);
        }
    }

    private void displayMessage(Message message) {
        DisplayMessageEvent.fire(SupportPresenter.this, message);
        getView().hide();
    }
}
