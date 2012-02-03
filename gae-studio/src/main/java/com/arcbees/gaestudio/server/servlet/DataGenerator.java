package com.arcbees.gaestudio.server.servlet;

import com.arcbees.gaestudio.server.domain.Sprocket;
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

        Objectify objectify = ObjectifyService.factory().begin();

        int numStatements = random.nextInt(50);
        for (int i = 0; i < numStatements; ++i) {
            objectify.query(Sprocket.class).filter("name", "Foobar").list();
        }
        
        logger.info("Fired off " + numStatements + " queries");

        request.getRequestDispatcher("/WEB-INF/jsp/dataGenerator.jsp").forward(request, response);
    }

}
