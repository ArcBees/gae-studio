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
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arcbees.gaestudio.server.guice.DispatchServletModule;
import com.arcbees.gaestudio.server.guice.GaeStudioServerModule;
import com.google.inject.Singleton;

@Singleton
public class EmbeddedStaticResourcesServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(EmbeddedStaticResourcesServlet.class.getSimpleName());
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        String message = "**** Find GAE Studio located at this path /" + DispatchServletModule.EMBEDDED_PATH + " ****";
        logger.info(message);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, 
            IOException {
        String uri = request.getRequestURI();
        uri = uri.replace(DispatchServletModule.EMBEDDED_PATH, "");

        String basePath = getBaseJarPath();

        String path = "";
        if (uri.length() < 3 || uri.contains("gae-studio.html")) {
            path = basePath + "/gae-studio.html";
        } else if (uri.contains("gae-studio.css")) {
            path = basePath + "/gae-studio.css";
        } else if (uri.contains("favicon.ico")) {
            path = basePath + "/favicon.ico";
        } else if (uri.contains("module_")) {
            path = basePath + uri; 
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
        Class<GaeStudioServerModule> clazz = GaeStudioServerModule.class;
        String classJarPath = clazz.getResource("GaeStudioServerModule.class").toString();
        String baseJarPath = classJarPath.substring(0, classJarPath.lastIndexOf("!") + 1) + "/META-INF";
        return baseJarPath;
    }

    public void writeFileToResponse(HttpServletResponse response, String path) throws MalformedURLException,
            IOException {
        InputStream inputStream = null;
        try {
            inputStream = new URL(path).openStream();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int b;
        while ((b = inputStream.read()) != -1) {
            response.getOutputStream().print((char) b);
        }
    }
}
