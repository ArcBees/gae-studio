package com.arcbees.gaestudio.server.servlet;

import com.arcbees.gaestudio.server.domain.Complex;
import com.arcbees.gaestudio.server.domain.Sprocket;
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
        
        initializeSeedData();

        Objectify objectify = ObjectifyService.factory().begin();
        
        int numStatements = random.nextInt(50);
        for (int i = 0; i < numStatements; ++i) {
            objectify.query(Sprocket.class).filter("name", "Sprocket #" + i).get();
        }
        
        logger.info("Fired off " + numStatements + " queries");

        request.getRequestDispatcher("/WEB-INF/jsp/dataGenerator.jsp").forward(request, response);
    }
    
    private void initializeSeedData() {
        Objectify objectify = ObjectifyService.factory().begin();

        if (objectify.query(Sprocket.class).count() == 0) {
            logger.info("Initializing seed data");
            for (int i = 0; i < 1500; ++i) {
                Sprocket sprocket = new Sprocket("Sprocket #" + i);
                objectify.put(sprocket);
                Complex complex = new Complex(KeyFactory.createKey("Sprocket", sprocket.getId()));
                objectify.put(complex);
            }
        }
    }

}
