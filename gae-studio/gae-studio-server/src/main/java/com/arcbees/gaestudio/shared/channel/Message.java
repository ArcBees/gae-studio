/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.channel;

import com.google.common.base.Objects;

public class Message {
    private final Topic topic;
    private final Object payload;

    public Message(Topic topic) {
        this(topic, null);
    }

    public Message(Topic topic, Object payload) {
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
        return Objects.toStringHelper(this)
                .add("topic", topic)
                .add("payload", payload)
                .toString();
    }
}
