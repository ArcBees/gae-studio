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

package com.arcbees.gaestudio.server.channel;

import javax.inject.Inject;

import com.arcbees.gaestudio.shared.channel.Message;
import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.gson.Gson;

public class ChannelMessageSenderImpl implements ChannelMessageSender {
    private final Gson gson;

    @Inject
    ChannelMessageSenderImpl(
            Gson gson) {
        this.gson = gson;
    }

    @Override
    public void sendMessage(String clientId, Message message) {
        String json = gson.toJson(message);
        ChannelService channelService = ChannelServiceFactory.getChannelService();
        ChannelMessage channelMessage = new ChannelMessage(clientId, json);
        channelService.sendMessage(channelMessage);
    }
}
