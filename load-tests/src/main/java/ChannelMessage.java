/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

public class ChannelMessage {
    String topic;
    String message;

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                .add("topic", topic)
                .add("message", message)
                .toString();
    }
}
