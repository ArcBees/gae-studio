package com.arcbees.gaestudio.server.guice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arcbees.gaestudio.server.velocity.VelocityWrapper;
import com.arcbees.gaestudio.server.velocity.VelocityWrapperFactory;
import com.arcbees.gaestudio.shared.BaseRestPath;
import com.google.inject.Singleton;

@Singleton
public class RootServlet extends HttpServlet {
    private final static String templateLocation =
            "com/arcbees/gaestudio/server/velocitytemplates/gae-studio.vm";

    protected final String restPath;

    private final VelocityWrapper velocityWrapper;

    @Inject
    public RootServlet(VelocityWrapperFactory velocityWrapperFactory,
                       @BaseRestPath String restPath) {
        this.restPath = restPath;
        this.velocityWrapper = velocityWrapperFactory.create(templateLocation);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();

        velocityWrapper.put("restPath", restPath);
        String generated = velocityWrapper.generate();

        printWriter.append(generated);
    }
}
