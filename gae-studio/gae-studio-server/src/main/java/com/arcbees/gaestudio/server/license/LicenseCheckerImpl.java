package com.arcbees.gaestudio.server.license;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;
import javax.inject.Provider;

import com.arcbees.gaestudio.server.service.auth.AuthService;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.oauth.client.domain.User;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class LicenseCheckerImpl implements LicenseChecker {
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

        return session().getLicenseValid() ? true : checkLicenseAgainstServer();
    }

    private Boolean checkLicenseAgainstServer() {
        URL url = getUrl();

        HTTPResponse response = getHttpResponse(url);

        if (response != null) {
            int responseCode = response.getResponseCode();

            session().setLicenseValid(responseCode == 200);
        } else {
            session().setLicenseValid(false);
        }

        return session().getLicenseValid();
    }

    private HTTPResponse getHttpResponse(URL url) {
        URLFetchService service = URLFetchServiceFactory.getURLFetchService();
        HTTPResponse response = null;
        try {
            response = service.fetch(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private URL getUrl() {
        User user = authService.checkLogin();
        Long userId = user.getId();

        URL url = null;
        try {
            url = new URL(EndPoints.ARCBEES_LICENSE_SERVICE + "check?id=" + userId);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
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
