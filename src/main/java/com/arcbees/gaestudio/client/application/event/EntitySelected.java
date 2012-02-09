package com.arcbees.gaestudio.client.application.event;

import com.arcbees.gaestudio.shared.dto.entity.Key;
import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
public class EntitySelected {
    
    @Order(1)
    Key entityKey;
    
    @Order(2)
    String entityData;

}