package com.arcbees.gaestudio.client.application.event;

import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
public class DisplayMessage {
    
    @Order(1)
    Message message;

}
