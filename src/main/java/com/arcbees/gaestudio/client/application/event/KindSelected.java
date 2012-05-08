package com.arcbees.gaestudio.client.application.event;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
public class KindSelected {
    
    @Order(1)
    String kind;

}
