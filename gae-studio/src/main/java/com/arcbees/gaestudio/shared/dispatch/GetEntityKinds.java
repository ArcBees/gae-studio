/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.shared.dispatch;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

import java.util.List;

@GenDispatch(isSecure = false)
public class GetEntityKinds {
    
    @In(1)
    Integer skip;
    
    @In(2)
    Integer take;

    @Out(1)
    List<String> kinds;

}
