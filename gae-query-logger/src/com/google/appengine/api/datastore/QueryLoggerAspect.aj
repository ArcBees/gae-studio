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

import com.arcbees.gae.querylogger.logger.QueryLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import javax.inject.Inject;
import javax.inject.Provider;

@Aspect
public class QueryLoggerAspect {

    @Inject
    private Provider<QueryLogger> queryLoggerProvider;

    @Pointcut("execution(* com.google.appengine.api.datastore.PreparedQueryImpl.runQuery(Query, FetchOptions))")
    public void runQueryExecution() {}

    @Before("runQueryExecution()")
    public void beforeRunQueryExecution(JoinPoint joinPoint) {
        Query query = (Query) joinPoint.getArgs()[0];
        FetchOptions fetchOptions = (FetchOptions) joinPoint.getArgs()[1];

        queryLoggerProvider.get().logQuery(query, fetchOptions);
    }

}
