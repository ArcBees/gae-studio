/*
 * Copyright (C) 2011 Google Inc.
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

package com.arcbees.gaestudio.client.channel;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * GWT implementation of {@link Channel}.
 */
public class ChannelImpl extends JavaScriptObject implements Channel {

    protected ChannelImpl() {
    }

    @Override
    public final native Socket open(SocketListener listener) /*-{
        var socket = this.open();

        socket.onopen = function (event) {
            listener.@com.arcbees.gaestudio.client.channel.SocketListener::onOpen(*)();
        };

        socket.onmessage = function (event) {
            listener.@com.arcbees.gaestudio.client.channel.SocketListener::onMessage(*)(event.data);
        };

        socket.onerror = function (error) {
            listener.@com.arcbees.gaestudio.client.channel.SocketListener::onError(*)(error);
        };

        socket.onclose = function () {
            listener.@com.arcbees.gaestudio.client.channel.SocketListener::onClose(*)();
        };

        return socket;
    }-*/;

}
