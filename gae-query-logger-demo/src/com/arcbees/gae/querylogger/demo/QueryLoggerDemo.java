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
import com.spoledge.audao.parser.gql.GqlDynamic;
import com.vercer.engine.persist.ObjectDatastore;
import com.vercer.engine.persist.annotation.AnnotationObjectDatastore;
import com.google.appengine.api.datastore.Query.FilterOperator;

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

        runObjectifyDemo();
        runTwigPersistDemo();
        runGqlDemo();
        runNPlusOneDemo();
        
        request.setAttribute("reportEntries", queryAnalyzerProvider.get().getReport());

        request.getRequestDispatcher("queryLoggerDemo.jsp").forward(request, response);
    }

    private void runGqlDemo() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        GqlDynamic gqlDynamic = new GqlDynamic();
        com.google.appengine.api.datastore.Query gqlQuery =
                gqlDynamic.parseQuery("SELECT * FROM Sprocket WHERE name=:1 LIMIT 1", "Cookies");

        datastore.prepare(gqlQuery).asSingleEntity();
    }

    private void runNPlusOneDemo() {
        Objectify objectify = ObjectifyService.factory().begin();
        for (int i = 0; i < 100; ++i) {
            objectify.query(Sprocket.class).filter("name", "Sprocket #" + i).get();
        }
    }

    private void runObjectifyDemo() {
        Objectify objectify = ObjectifyService.factory().begin();
        objectify.query(Sprocket.class).filter("name", "Foobar").order("-name").get();
    }
    
    private void runTwigPersistDemo() {
        ObjectDatastore datastore = new AnnotationObjectDatastore();
        datastore.find().type(Sprocket.class)
                .addFilter("name", FilterOperator.EQUAL, "Foobar")
                .addSort("-name")
                .returnResultsNow();
    }

}
