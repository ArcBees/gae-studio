/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.testutil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.mockito.ArgumentCaptor;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.dev.UploadBlobServlet;
import com.google.appengine.tools.development.ApiProxyLocalFactory;
import com.google.appengine.tools.development.LocalServerEnvironment;
import com.google.inject.Inject;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BlobGenerator {
    private static final String BLOB_KEYS = "com.google.appengine.api.blobstore.upload.blobkeys";
    private static final String MULTI_PART_BOUNDARY =
            "multipart/form-data; boundary=----WebKitFormBoundary8wvYWW5KwfiI9Lzk";

    private final BlobstoreService blobstoreService;

    @Inject
    BlobGenerator(BlobstoreService blobstoreService) {
        this.blobstoreService = blobstoreService;
    }

    public BlobKey storeBlobFromFile(String path) throws IOException, ServletException {
        UploadBlobServlet uploadBlobServlet = new UploadBlobServlet();

        HttpServletRequest request = mockHttpServletRequest();
        ServletConfig servletConfig = mockServletConfig();

        writeDataToRequest(path, request);

        uploadBlobServlet.init(servletConfig);
        uploadBlobServlet.doPost(request, mock(HttpServletResponse.class));

        return extractBlobKey(request);
    }

    private HttpServletRequest mockHttpServletRequest() {
        String uploadUrl = blobstoreService.createUploadUrl("/upload");
        uploadUrl = uploadUrl.substring(uploadUrl.lastIndexOf("/"));

        HttpServletRequest request = mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
        when(request.getPathInfo()).thenReturn(uploadUrl);
        when(request.getContentType()).thenReturn(MULTI_PART_BOUNDARY);

        return request;
    }

    private ServletConfig mockServletConfig() {
        ServletConfig servletConfig = mock(ServletConfig.class);
        ServletContext servletContext = mock(ServletContext.class, RETURNS_DEEP_STUBS);

        when(servletContext.getAttribute(eq("com.google.appengine.devappserver.ApiProxyLocal")))
                .thenReturn(new ApiProxyLocalFactory().create(mock(LocalServerEnvironment.class, RETURNS_DEEP_STUBS)));
        when(servletConfig.getServletContext()).thenReturn(servletContext);

        return servletConfig;
    }

    private void writeDataToRequest(String path, HttpServletRequest request) throws IOException {
        InputStream stream = getClass().getResourceAsStream(path);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(("------WebKitFormBoundary8wvYWW5KwfiI9Lzk\n" +
                "Content-Disposition: form-data; name=\"fileUpload\"; filename=\"filename\"\n" +
                "Content-Type: application/octet-stream\n\n").getBytes());

        IOUtils.copy(stream, out);

        out.write("\n\n------WebKitFormBoundary8wvYWW5KwfiI9Lzk".getBytes());

        when(request.getInputStream()).thenReturn(
                new ServletInputStreamImpl(new ByteArrayInputStream(out.toByteArray())));
    }

    private BlobKey extractBlobKey(HttpServletRequest request) {
        ArgumentCaptor<Map> requestCaptor = ArgumentCaptor.forClass(Map.class);
        verify(request).setAttribute(eq(BLOB_KEYS), requestCaptor.capture());

        Map<String, List<String>> uploads = requestCaptor.getValue();

        for (List<String> blobKeys : uploads.values()) {
            return new BlobKey(blobKeys.get(0));
        }

        return null;
    }

    private static class ServletInputStreamImpl extends ServletInputStream {
        private final InputStream inputStream;

        ServletInputStreamImpl(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }
    }
}
