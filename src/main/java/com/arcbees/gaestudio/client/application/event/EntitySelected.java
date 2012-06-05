package com.arcbees.gaestudio.client.application.event;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
public class EntitySelected {
    
    @Order(1)
    ParsedEntity parsedEntity;

}
