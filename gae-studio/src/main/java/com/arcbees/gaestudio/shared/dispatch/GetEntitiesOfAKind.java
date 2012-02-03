/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.shared.dispatch;

import com.arcbees.gaestudio.shared.dto.EntityDto;
import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

import java.util.List;

@GenDispatch(isSecure = false)
public class GetEntitiesOfAKind {

    @In(1)
    String kind;
    
    @In(2)
    Integer skip;
    
    @In(3)
    Integer take;

    @Out(1)
    List<EntityDto> entities;
    
}
