package com.arcbees.gaestudio.client.rest;

import javax.inject.Inject;

import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestService;
import org.fusesource.restygwt.client.RestServiceProxy;

import com.arcbees.gaestudio.shared.rest.EndPoints;

public class ResourceFactory {
    @Inject
    public ResourceFactory() {
        Defaults.setDateFormat(null);
    }

    public <P extends RestService> P setupProxy(P proxy, String endPoint) {
        Resource resource = new Resource(EndPoints.REST_PATH + endPoint);
        ((RestServiceProxy) proxy).setResource(resource);

        return proxy;
    }
}
