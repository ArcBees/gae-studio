/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.guice;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arcbees.gaestudio.server.velocity.VelocityWrapper;
import com.arcbees.gaestudio.server.velocity.VelocityWrapperFactory;
import com.arcbees.gaestudio.shared.BaseRestPath;
import com.arcbees.gaestudio.shared.ExpirationDate;
import com.google.inject.Singleton;

@Singleton
public class RootServlet extends HttpServlet {
    private final static String templateLocation =
            "com/arcbees/gaestudio/server/velocitytemplates/gae-studio.vm";

    protected final String restPath;

    private final Date expirationDate;
    private final VelocityWrapper velocityWrapper;

    @Inject
    RootServlet(VelocityWrapperFactory velocityWrapperFactory,
                @BaseRestPath String restPath,
                @ExpirationDate Date expirationDate) {
        this.restPath = restPath;
        this.expirationDate = expirationDate;
        this.velocityWrapper = velocityWrapperFactory.create(templateLocation);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();

        velocityWrapper.put("restPath", restPath);
        velocityWrapper.put("expirationDate", expirationDate.getTime());
        String generated = velocityWrapper.generate();

        printWriter.append(generated);
    }
}
