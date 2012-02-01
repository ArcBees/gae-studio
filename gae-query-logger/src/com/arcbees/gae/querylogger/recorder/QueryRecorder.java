/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gae.querylogger.recorder;

import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;

public interface QueryRecorder {
    
    void recordQuery(Query query, FetchOptions fetchOptions);
    
}
