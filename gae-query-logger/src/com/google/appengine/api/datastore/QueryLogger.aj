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

package com.google.appengine.api.datastore;

import com.arcbees.gae.querylogger.QueryCollector;
import com.arcbees.gae.querylogger.QueryToString;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.logging.Logger;

@Aspect
public class QueryLogger {

    @Inject
    private Logger logger;

    @Inject
    private Provider<QueryCollector> queryCollectorProvider;

    @Pointcut("execution(* com.google.appengine.api.datastore.PreparedQueryImpl.runQuery(Query, FetchOptions))")
    public void runQueryExecution() {}

    @Before("runQueryExecution()")
    public void beforeRunQuery(JoinPoint joinPoint) {
        Query query = (Query) joinPoint.getArgs()[0];
        FetchOptions fetchOptions = (FetchOptions) joinPoint.getArgs()[1];

        QueryCollector queryCollector = queryCollectorProvider.get();
        queryCollector.collectQuery(query, fetchOptions);

        logger.info(QueryToString.queryToString(query, fetchOptions));
    }

}
