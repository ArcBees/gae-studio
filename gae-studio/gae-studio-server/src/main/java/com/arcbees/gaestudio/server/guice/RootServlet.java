/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.guice;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arcbees.gaestudio.server.BuildConstants;
import com.arcbees.gaestudio.server.analytic.UseCookieDomainNone;
import com.arcbees.gaestudio.server.velocity.VelocityWrapper;
import com.arcbees.gaestudio.server.velocity.VelocityWrapperFactory;
import com.arcbees.gaestudio.shared.BaseRestPath;
import com.arcbees.gaestudio.shared.config.AppConfig;
import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.gson.Gson;
import com.google.inject.Singleton;

@Singleton
public class RootServlet extends HttpServlet {
    private final static String templateLocation =
            "com/arcbees/gaestudio/server/velocitytemplates/gae-studio.vm";
    private final static String MAVEN_URL = "http://search.maven.org/solrsearch/select?wt=json&q=gae-studio-webapp";
    private final static Pattern LATEST_VERSION_PATTERN =
            Pattern.compile("\"latestVersion\":\\s*\"(.*?)\"");
    private final static Pattern RESPONSE_CONTENT_PATTERN = Pattern.compile("^[^{]*(\\{.*\\})$");
    private final static String COULD_NOT_RETRIEVE_LATEST_VERSION = "Could not retrieve latest version";

    protected final String restPath;
    private final VelocityWrapper velocityWrapper;
    private final boolean useCookieDomainNone;

    @Inject
    RootServlet(VelocityWrapperFactory velocityWrapperFactory,
            @BaseRestPath String restPath,
            @UseCookieDomainNone boolean useCookieDomainNone) {
        this.restPath = restPath;
        this.useCookieDomainNone = useCookieDomainNone;
        this.velocityWrapper = velocityWrapperFactory.create(templateLocation);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();

        setParameters();

        String generated = velocityWrapper.generate();

        printWriter.append(generated);
    }

    private void setParameters() {
        AppConfig config = buildAppConfig();
        String json = new Gson().toJson(config);
        velocityWrapper.put(AppConfig.OBJECT_KEY, "var " + AppConfig.OBJECT_KEY + " = " + json + ";");
    }

    private AppConfig buildAppConfig() {
        return AppConfig.with()
                .restPath(restPath)
                .version(BuildConstants.VERSION)
                .latestVersion(retrieveLatestVersion())
                .useCookieDomainNone(useCookieDomainNone)
                .build();
    }

    private String retrieveLatestVersion() {
        URL maven;

        try {
            maven = new URL(MAVEN_URL);
        } catch (MalformedURLException e) {
            return COULD_NOT_RETRIEVE_LATEST_VERSION;
        }

        URLFetchService urlFetchService = URLFetchServiceFactory.getURLFetchService();

        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();

        HTTPRequest request = new HTTPRequest(maven, HTTPMethod.GET, fetchOptions);

        Future future = urlFetchService.fetchAsync(request);

        HTTPResponse httpResponse;
        try {
            httpResponse = (HTTPResponse) future.get();
        } catch (InterruptedException e) {
            return COULD_NOT_RETRIEVE_LATEST_VERSION;
        } catch (ExecutionException e) {
            return COULD_NOT_RETRIEVE_LATEST_VERSION;
        }

        String json = extractResponseContent(new String(httpResponse.getContent()));

        return extractLatestVersion(json);
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
