/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.channel;

import java.util.List;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.rest.ChannelService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.channel.Token;
import com.arcbees.gaestudio.shared.channel.Topic;
import com.google.gwt.appengine.channel.client.Channel;
import com.google.gwt.appengine.channel.client.ChannelError;
import com.google.gwt.appengine.channel.client.ChannelFactory;
import com.google.gwt.appengine.channel.client.Socket;
import com.google.gwt.appengine.channel.client.SocketListener;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.gwtplatform.dispatch.rest.client.RestDispatch;
import com.gwtplatform.dispatch.rest.shared.RestAction;

import static com.google.common.collect.Lists.newArrayList;

public class ChannelHandler implements SocketListener {
    public interface ChannelOpenCallback {
        void onChannelOpen();
    }

    private final ChannelFactory channelFactory;
    private final ChannelService channelService;
    private final RestDispatch dispatch;
    private final List<ChannelOpenCallback> callbacks;
    private final MessageHandlers messageHandlers;

    private Socket socket;
    private boolean channelOpen;

    @Inject
    ChannelHandler(ChannelService channelService,
                   RestDispatch dispatch,
                   ChannelFactory channelFactory,
                   MessageHandlers messageHandlers) {
        this.channelFactory = channelFactory;
        this.dispatch = dispatch;
        this.channelService = channelService;
        this.messageHandlers = messageHandlers;

        callbacks = newArrayList();
    }

    public void openChannel(final ChannelOpenCallback callback) {
        if (isChannelOpen()) {
            callback.onChannelOpen();
            return;
        }

        callbacks.add(callback);

        RestAction<Token> action = channelService.getToken();
        dispatch.execute(action, new AsyncCallbackImpl<Token>() {
            @Override
            public void onSuccess(Token token) {
                Channel channel = channelFactory.createChannel(token.getValue());

                socket = channel.open(ChannelHandler.this);
            }
        });
    }

    public void closeChannel() {
        if (socket != null) {
            socket.close();
        }
    }

    public boolean isChannelOpen() {
        return channelOpen;
    }

    @Override
    public void onOpen() {
        for (ChannelOpenCallback callback : callbacks) {
            callback.onChannelOpen();
        }

        channelOpen = true;
    }

    @Override
    public void onMessage(String json) {
        JSONValue jsonValue = JSONParser.parseStrict(json);
        JSONObject jsonObject = jsonValue.isObject();
        String topicString = jsonObject.get("topic").isString().stringValue();
        Topic topic = Topic.valueOf(topicString);
        JSONValue payload = jsonObject.get("payload");

        messageHandlers.handle(topic, payload);
    }

    @Override
    public void onError(ChannelError channelError) {
    }

    @Override
    public void onClose() {
        channelOpen = false;
    }
}
