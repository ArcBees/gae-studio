/*
 * Copyright 2011 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.gae.querylogger;

import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.logging.Logger;

// TODO see about using @Aspect instead
public aspect QueryLogger {

    @Inject
    Logger logger;
    
    @Inject
    Provider<QueryCollector> queryCollectorProvider;

    // TODO figure out why this is issuing a warning about some advice not being applied
    @Before("execution(* com.google.appengine.api.datastore.PreparedQueryImpl.runQuery(Query, FetchOptions))")
    public void logQuery(JoinPoint joinPoint) {
        Query query = (Query) joinPoint.getArgs()[0];
        FetchOptions fetchOptions = (FetchOptions) joinPoint.getArgs()[1];

        QueryCollector queryCollector = queryCollectorProvider.get();
        queryCollector.collectQuery(query, fetchOptions);

        logger.info(QueryToString.queryToString(query, fetchOptions));
    }

}
