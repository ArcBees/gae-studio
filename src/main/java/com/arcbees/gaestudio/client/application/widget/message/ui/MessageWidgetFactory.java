package com.arcbees.gaestudio.client.application.widget.message.ui;

import com.arcbees.gaestudio.client.application.widget.message.Message;

public interface MessageWidgetFactory {

    public MessageWidget createMessage(Message message);

}
