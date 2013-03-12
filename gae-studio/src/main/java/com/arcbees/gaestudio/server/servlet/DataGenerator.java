/**
 * Copyright 2013 ArcBees Inc.
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

package com.arcbees.gaestudio.server.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arcbees.gaestudio.server.domain.Car;
import com.arcbees.gaestudio.server.domain.Currency;
import com.arcbees.gaestudio.server.domain.Driver;
import com.arcbees.gaestudio.server.domain.Money;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

@Singleton
public class DataGenerator extends HttpServlet {
    private final Logger logger;

    private final Random random;

    @Inject
    public DataGenerator(final Logger logger) {
        this.logger = logger;

        random = new Random();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, 
        IOException {
        Objectify objectify = ObjectifyService.factory().begin();
        initializeSeedData(objectify);

        int numStatements = random.nextInt(10);
        for (int i = 0; i < numStatements; ++i) {
            objectify.load().type(Car.class).filter("year", 2000 + i).first().get();
        }

        logger.info("Fired off " + numStatements + " queries");

        request.getRequestDispatcher("/WEB-INF/jsp/dataGenerator.jsp").forward(request, response);
    }

    private void initializeSeedData(Objectify objectify) {
        if (objectify.load().type(Driver.class).count() == 0) {
            logger.info("Initializing seed data");
            for (int i = 0; i < 50; ++i) {
                Money accountBalance = new Money(i + 1.5d, Currency.USD);
                Driver driver = new Driver("John", "Doe #" + i, new Date(), accountBalance);
                objectify.save().entities(driver);

                Key driverKey = KeyFactory.createKey("Driver", driver.getId());
                Car car = new Car("Tooa", "Tera", 2000 + i % 10, driverKey);
                objectify.save().entities(car);
            }
        }
    }
}
