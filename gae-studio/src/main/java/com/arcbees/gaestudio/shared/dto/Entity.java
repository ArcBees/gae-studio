/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.shared.dto;

import com.google.gson.reflect.TypeToken;
import com.gwtplatform.dispatch.annotation.GenDto;
import com.gwtplatform.dispatch.annotation.Order;

@GenDto
public class Entity {

    @Order(1)
    TypeToken typeToken;
    
    @Order(2)
    String jsonData;

}
