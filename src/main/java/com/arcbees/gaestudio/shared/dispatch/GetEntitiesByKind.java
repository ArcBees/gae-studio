/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.shared.dispatch;

import com.arcbees.gaestudio.shared.dto.entity.EntityDTO;
import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Optional;
import com.gwtplatform.dispatch.annotation.Out;

import java.util.ArrayList;

@GenDispatch(isSecure = false)
public class GetEntitiesByKind {
    
    @In(1)
    String kind;
    
    @Optional
    @In(2)
    Integer offset;
    
    @Optional
    @In(3)
    Integer limit;
    
    @Out(1)
    ArrayList<EntityDTO> entities;

}
