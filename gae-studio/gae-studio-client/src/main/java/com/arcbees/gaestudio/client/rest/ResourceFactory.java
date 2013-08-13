/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.rest;

import javax.inject.Inject;

import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestService;
import org.fusesource.restygwt.client.RestServiceProxy;

import com.arcbees.gaestudio.shared.BaseRestPath;

public class ResourceFactory {
    private final String baseRestPath;

    @Inject
    ResourceFactory(@BaseRestPath String baseRestPath) {
        this.baseRestPath = baseRestPath;

        Defaults.setDateFormat(null);
    }

    public <P extends RestService> P setupProxy(P proxy, String endPoint) {
        Resource resource = new Resource(baseRestPath + endPoint);
        ((RestServiceProxy) proxy).setResource(resource);

        return proxy;
    }
}
