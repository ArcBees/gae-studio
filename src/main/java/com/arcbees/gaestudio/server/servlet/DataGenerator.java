package com.arcbees.gaestudio.server.servlet;

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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.logging.Logger;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Objectify objectify = ObjectifyService.factory().begin();
        initializeSeedData(objectify);

        int numStatements = random.nextInt(10);
        for (int i = 0; i < numStatements; ++i) {
            objectify.query(Car.class).filter("year", 2000 + i).get();
        }

        logger.info("Fired off " + numStatements + " queries");

        request.getRequestDispatcher("/WEB-INF/jsp/dataGenerator.jsp").forward(request, response);
    }

    private void initializeSeedData(Objectify objectify) {
        if (objectify.query(Driver.class).count() == 0) {
            logger.info("Initializing seed data");
            for (int i = 0; i < 50; ++i) {
                Money accountBalance = new Money(i + 1.5d, Currency.USD);
                Driver driver = new Driver("John", "Doe #" + i, new Date(), accountBalance);
                objectify.put(driver);

                Key driverKey = KeyFactory.createKey("Driver", driver.getId());
                Car car = new Car("Tooa", "Tera", 2000 + i % 10, driverKey);
                objectify.put(car);
            }
        }
    }

}
