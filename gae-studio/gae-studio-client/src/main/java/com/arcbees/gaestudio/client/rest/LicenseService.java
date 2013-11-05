package com.arcbees.gaestudio.client.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;

public interface LicenseService extends RestService {
    @GET
    @Path(EndPoints.CHECK)
    void checkLicense(@QueryParam(UrlParameters.ID) Long userId, MethodCallback<Void> callback);

    @POST
    @Path(EndPoints.REGISTER)
    void register(LicenseRegistration licenseRegistration, MethodCallback<Void> callback);
}
