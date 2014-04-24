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
import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;
import javax.inject.Provider;

import com.arcbees.gaestudio.server.exception.UserNotLoggedInException;
import com.arcbees.gaestudio.server.service.auth.AuthService;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.oauth.client.domain.User;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class LicenseCheckerImpl implements LicenseChecker {
    private static final double GAE_MAXIMUM_HTTP_REQUEST_DEADLINE = 60d;

    private final AuthService authService;
    private final Provider<LicenseSession> licenseSessionProvider;

    @Inject
    LicenseCheckerImpl(AuthService authService,
                       Provider<LicenseSession> licenseSessionProvider) {
        this.authService = authService;
        this.licenseSessionProvider = licenseSessionProvider;
    }

    @Override
    public Boolean isLicenseValid() {
        initializeSession();

        if (session().getLicenseValid()) {
            return true;
        } else {
            Boolean isLicenseValid = checkLicenseAgainstServer();
            session().setLicenseValid(isLicenseValid);

            return isLicenseValid;
        }
    }

    private Boolean checkLicenseAgainstServer() {
        URL url = getUrl();

        HTTPResponse response = getHttpResponse(url);

        int responseCode = response.getResponseCode();

        return responseCode == 200;
    }

    private HTTPResponse getHttpResponse(URL url) {
        URLFetchService service = URLFetchServiceFactory.getURLFetchService();

        HTTPRequest httpRequest = new HTTPRequest(url, HTTPMethod.GET);
        httpRequest.getFetchOptions().setDeadline(GAE_MAXIMUM_HTTP_REQUEST_DEADLINE);

        try {
            return service.fetch(httpRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URL getUrl() {
        User user = authService.checkLogin();

        if (user == null) {
            throw new UserNotLoggedInException();
        }

        Long userId = user.getId();

        try {
            return new URL(EndPoints.ARCBEES_LICENSE_SERVICE + "check?id=" + userId);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeSession() {
        if (session().getLicenseValid() == null) {
            session().setLicenseValid(false);
        }
    }

    private LicenseSession session() {
        return licenseSessionProvider.get();
    }
}
