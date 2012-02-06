/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.shared.dispatch;

import com.arcbees.gaestudio.shared.dto.DbOperationRecord;
import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Optional;
import com.gwtplatform.dispatch.annotation.Out;

import java.util.ArrayList;

@GenDispatch(isSecure = false)
public class GetNewDbOperationRecords {

    @In(1)
    Long lastId;
    
    @Optional
    @In(2)
    Integer maxResults;
    
    @Out(1)
    Long newLastId;

    @Out(2)
    ArrayList<DbOperationRecord> records;
    
}
