package com.arcbees.gaestudio.server.license;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

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

        Boolean isLicenseValid = checker.isLicenseValid();

        if (!isLicenseValid) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private boolean isAllowedPath(String path) {
        return path.contains(EndPoints.AUTH);
    }
}
