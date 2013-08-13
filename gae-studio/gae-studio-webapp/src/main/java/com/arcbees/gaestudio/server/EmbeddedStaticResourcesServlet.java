/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Singleton
public class EmbeddedStaticResourcesServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(EmbeddedStaticResourcesServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        logger.info("request.getRequestURI: " + uri);
        uri = uri.replace("/gae-studio-admin/", "");

        String basePath = getBaseJarPath();
        logger.info("getBaseJarPath(); " + basePath);

        String path;
        if (uri.isEmpty()) {
            path = basePath + "gae-studio.html";
        } else {
            path = basePath + uri;
        }

        response.setContentType(getMimeType(path));

        writeFileToResponse(response, path);
    }

    public String getMimeType(String path) {
        path = path.toLowerCase();

        String mimeType = "text/plain";
        if (path.contains(".html")) {
            mimeType = "text/html";
        } else if (path.contains(".ico")) {
            mimeType = "image/x-icon";
        } else if (path.contains(".css")) {
            mimeType = "text/css";
        } else if (path.contains(".jpeg") || path.contains(".jpg")) {
            mimeType = "image/jpeg";
        } else if (path.contains(".png")) {
            mimeType = "image/png";
        } else if (path.contains(".gif")) {
            mimeType = "image/gif";
        } else if (path.contains(".js")) {
            mimeType = "application/javascript";
        }

        return mimeType;
    }

    public String getBaseJarPath() {
        Class<EmbeddedStaticResourcesServlet> clazz = EmbeddedStaticResourcesServlet.class;
        String classJarPath = clazz.getResource(clazz.getSimpleName() + ".class").toString();

        return classJarPath.substring(0, classJarPath.lastIndexOf("!") + 1) + "/";
    }

    public void writeFileToResponse(HttpServletResponse response, String path) throws IOException {
        InputStream inputStream = new URL(path).openStream();

        int b;
        while ((b = inputStream.read()) != -1) {
            response.getOutputStream().print((char) b);
        }
    }
}
