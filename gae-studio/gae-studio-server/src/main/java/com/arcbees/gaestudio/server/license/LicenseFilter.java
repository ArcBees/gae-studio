/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.license;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.arcbees.gaestudio.server.guice.devserver.BlobUploadFilter;
import com.arcbees.gaestudio.shared.rest.EndPoints;

@Provider
public class LicenseFilter implements ContainerRequestFilter {
    private final LicenseChecker checker;

    @Inject
    LicenseFilter(LicenseChecker checker) {
        this.checker = checker;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();

        if (isAllowedPath(path)) {
            return;
        }

        if (!isBlobUpload()) {
            Boolean isLicenseValid = checker.isLicenseValid();

            if (!isLicenseValid) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        }
    }

    private boolean isAllowedPath(String path) {
        return path.contains(EndPoints.AUTH) || path.contains(EndPoints.IMPORT_TASK);
    }

    private boolean isBlobUpload() {
        Class<?> myClass = BlobUploadFilter.class;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        for (StackTraceElement element : stackTrace) {
            if (element.getClassName().equals(myClass.getCanonicalName())) {
                return true;
            }
        }

        return false;
    }
}
