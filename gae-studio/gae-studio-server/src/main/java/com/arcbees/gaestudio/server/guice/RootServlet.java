/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.guice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arcbees.gaestudio.server.BuildConstants;
import com.arcbees.gaestudio.server.velocity.VelocityWrapper;
import com.arcbees.gaestudio.server.velocity.VelocityWrapperFactory;
import com.arcbees.gaestudio.shared.BaseRestPath;
import com.arcbees.gaestudio.shared.config.AppConfig;
import com.google.gson.Gson;
import com.google.inject.Singleton;

@Singleton
public class RootServlet extends HttpServlet {
    private final static String templateLocation =
            "com/arcbees/gaestudio/server/velocitytemplates/gae-studio.vm";
    private final static String MAVEN_URL = "http://search.maven.org/solrsearch/select?wt=json&q=gae-studio-webapp";
    private final static Pattern LATEST_VERSION_PATTERN = Pattern.compile("\"latestVersion\":\\s*\"([0-9](?:\\.[0-9])*)\"");
    private final static Pattern RESPONSE_CONTENT_PATTERN = Pattern.compile("^[^{]*(\\{.*\\})$");

    protected final String restPath;
    private final VelocityWrapper velocityWrapper;

    @Inject
    RootServlet(VelocityWrapperFactory velocityWrapperFactory,
                @BaseRestPath String restPath) {
        this.restPath = restPath;
        this.velocityWrapper = velocityWrapperFactory.create(templateLocation);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();

        setParameters();

        String generated = velocityWrapper.generate();

        printWriter.append(generated);
    }

    private void setParameters() throws IOException {
        AppConfig config = buildAppConfig();
        String json = new Gson().toJson(config);
        velocityWrapper.put(AppConfig.OBJECT_KEY, "var " + AppConfig.OBJECT_KEY + " = " + json + ";");
    }

    private AppConfig buildAppConfig() throws IOException {
        return AppConfig.with()
                    .restPath(restPath)
                    .clientId(UUID.randomUUID().toString())
                    .version(BuildConstants.VERSION)
                    .latestVersion(retrieveLatestVersion())
                    .build();
    }

    private String retrieveLatestVersion() throws IOException {
        String json;

        URL maven = new URL(MAVEN_URL);

        try (Socket socket = new Socket(maven.getHost(), 80)) {
            PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
            output.println("GET " + maven.getFile() + " HTTP/1.1");
            output.println("Host: " + maven.getHost());
            output.println("Connection: close");
            output.println();
            output.flush();

            json = readJson(socket.getInputStream());
        }

        return extractLatestVersion(json);
    }

    private String readJson(InputStream inputStream) throws IOException {
        String response = "";
        BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));

        String inputLine;
        while((inputLine = input.readLine()) != null) {
            response += inputLine;
        }

        return extractResponseContent(response);
    }

    private String extractResponseContent(String response) {
        Matcher matcher = RESPONSE_CONTENT_PATTERN.matcher(response);
        matcher.find();

        return matcher.group(1);
    }

    private String extractLatestVersion(String json) {
        Matcher matcher = LATEST_VERSION_PATTERN.matcher(json);
        matcher.find();

        return matcher.group(1);
    }
}
