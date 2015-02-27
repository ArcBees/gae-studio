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

package com.arcbees.gaestudio.shared.channel;

import com.google.common.base.MoreObjects;

public class Message {
    private final Topic topic;
    private final Object payload;

    public Message(
            Topic topic) {
        this(topic, null);
    }

    public Message(
            Topic topic,
            Object payload) {
        this.topic = topic;
        this.payload = payload;
    }

    public Topic getTopic() {
        return topic;
    }

    public Object getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("topic", topic)
                .add("payload", payload)
                .toString();
    }
}
