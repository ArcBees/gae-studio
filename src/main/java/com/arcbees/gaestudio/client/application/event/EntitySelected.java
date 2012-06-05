package com.arcbees.gaestudio.client.application.event;

import com.arcbees.gaestudio.client.domain.EntityJsonParsed;
import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
public class EntitySelected {
    
    @Order(1)
    EntityJsonParsed entityJsonParsed;

}
