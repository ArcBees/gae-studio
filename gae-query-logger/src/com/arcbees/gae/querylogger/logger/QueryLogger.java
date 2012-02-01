/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gae.querylogger.logger;

import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;

public interface QueryLogger {
    
    void logQuery(Query query, FetchOptions fetchOptions);
    
}
