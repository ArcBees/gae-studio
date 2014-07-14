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

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.query.client.GQuery;
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

    @Inject
    SupportPresenter(EventBus eventBus,
                     MyView view,
                     MessageRequestMapper messageRequestMapper) {
        super(eventBus, view);

        this.messageRequestMapper = messageRequestMapper;

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

        requestBuilder.setHeader("Content-Type", "application/json");
        requestBuilder.setHeader("Authorization", API_KEY);

        String requestData = messageRequestMapper.write(MessageRequest.fromSupportMessage(supportMessage));
        GQuery.console.info(requestData);

        try {
            requestBuilder.sendRequest(requestData, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    // todo: response
                }

                @Override
                public void onError(Request request, Throwable exception) {
                    // todo: log error
                }
            });
        } catch (RequestException e) {
            // todo: log error
        }
    }
}
