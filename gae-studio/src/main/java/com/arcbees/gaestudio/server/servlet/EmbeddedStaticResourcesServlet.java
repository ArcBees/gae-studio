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
        System.out.println("uri=" + request.getRequestURI());

        String uri = request.getRequestURI();
        uri = uri.replace(DispatchServletModule.EMBEDDED_PATH, "");

        String basePath = getBaseJarPath();

        System.out.println("basePath=" + basePath);

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

        System.out.println("path=" + path);

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

        System.out.println("mime=" + mimeType);

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
