package com.arcbees.gaestudio.client.application.profiler.event;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
public class RequestSelected {
    
    @Order(1)
    Long requestId;
    
}
