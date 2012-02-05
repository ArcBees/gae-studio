package com.arcbees.gaestudio.client.application.event;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
public class StatementSelected {
    
    @Order(1)
    Long statementId;
    
}
