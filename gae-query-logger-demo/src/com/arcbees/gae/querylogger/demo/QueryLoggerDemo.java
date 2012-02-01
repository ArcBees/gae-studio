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

package com.arcbees.gae.querylogger.demo;

import com.arcbees.gae.querylogger.analyzer.QueryAnalyzer;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.spoledge.audao.parser.gql.GqlDynamic;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Singleton
public class QueryLoggerDemo extends HttpServlet {

    private final Logger logger;
    
    private final Provider<QueryAnalyzer> queryAnalyzerProvider;

    @Inject
    public QueryLoggerDemo(final Logger logger, final Provider<QueryAnalyzer> queryAnalyzerProvider) {
        this.logger = logger;
        this.queryAnalyzerProvider = queryAnalyzerProvider;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Simulate an Objectify query
        Objectify objectify = ObjectifyService.factory().begin();

        Query<Sprocket> query = objectify.query(Sprocket.class).filter("name", "Foobar").order("-name");
        query.get();

        // Simulate a GQL query
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        GqlDynamic gqlDynamic = new GqlDynamic();
        com.google.appengine.api.datastore.Query gqlQuery =
                gqlDynamic.parseQuery("SELECT * FROM Sprocket WHERE name=:1 LIMIT 1", "Cookies");

        datastore.prepare(gqlQuery).asSingleEntity();
        
        // Simulate an N+1 scenario
//        long sprocketIds[] = new long[100];
//        for (int i = 0; i < 100; ++i) {
//            Sprocket sprocket = new Sprocket("Sprocket #" + i);
//            sprocketIds[i] = objectify.put(sprocket).getId();
//        }
//
//        objectify = ObjectifyService.factory().begin();

        for (int i = 0; i < 100; ++i) {
            objectify.query(Sprocket.class).filter("name", "Sprocket #" + i).get();
        }
        
        request.setAttribute("reportEntries", queryAnalyzerProvider.get().getReport());

        request.getRequestDispatcher("queryLoggerDemo.jsp").forward(request, response);
    }

}
