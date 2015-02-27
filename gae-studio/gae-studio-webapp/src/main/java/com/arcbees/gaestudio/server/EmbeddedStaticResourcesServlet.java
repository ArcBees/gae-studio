/**
 * Copyright 2015 ArcBees Inc.
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

package com.arcbees.gaestudio.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Singleton
public class EmbeddedStaticResourcesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        uri = uri.replace("/gae-studio-admin/", "");

        String basePath = getBaseJarPath();

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
        String lowerCasePath = path.toLowerCase();

        String mimeType = "text/plain";
        if (lowerCasePath.contains(".html")) {
            mimeType = "text/html";
        } else if (lowerCasePath.contains(".ico")) {
            mimeType = "image/x-icon";
        } else if (lowerCasePath.contains(".css")) {
            mimeType = "text/css";
        } else if (lowerCasePath.contains(".jpeg") || lowerCasePath.contains(".jpg")) {
            mimeType = "image/jpeg";
        } else if (lowerCasePath.contains(".png")) {
            mimeType = "image/png";
        } else if (lowerCasePath.contains(".gif")) {
            mimeType = "image/gif";
        } else if (lowerCasePath.contains(".js")) {
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
