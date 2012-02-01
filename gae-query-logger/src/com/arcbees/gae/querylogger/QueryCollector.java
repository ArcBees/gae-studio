/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gae.querylogger;

import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;

public interface QueryCollector {
    
    void logQuery(Query query, FetchOptions fetchOptions);
    
}
