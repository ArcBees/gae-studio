/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.shared.dispatch;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Optional;
import com.gwtplatform.dispatch.annotation.Out;

import java.util.ArrayList;

@GenDispatch(isSecure = false)
public class GetEntityKinds {

    @Out(1)
    ArrayList<String> kinds;
    
}
