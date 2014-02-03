/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.widget.message;

public class Message {
    private final String message;
    private final MessageStyle style;
    private final MessageCloseDelay closeDelay;

    public Message(String message,
                   MessageStyle style) {
        this(message, style, MessageCloseDelay.DEFAULT);
    }

    public Message(String message,
                   MessageStyle style,
                   MessageCloseDelay closeDelay) {
        this.message = message;
        this.style = style;
        this.closeDelay = closeDelay;
    }

    public MessageStyle getStyle() {
        return style;
    }

    public String getMessage() {
        return message;
    }

    public MessageCloseDelay getCloseDelay() {
        return closeDelay;
    }
}
