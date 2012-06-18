package com.arcbees.gaestudio.client.application.event;

import com.arcbees.gaestudio.shared.dto.entity.EntityDTO;
import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
public class EntitySaved {

    @Order(1)
    EntityDTO entityDTO;

}
