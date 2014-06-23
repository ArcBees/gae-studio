/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.rest;

import javax.inject.Inject;

import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.gwtplatform.dispatch.rest.client.RestApplicationPath;

public class ExportService {
    private final String restPath;

    @Inject
    ExportService(@RestApplicationPath String restPath) {
        this.restPath = restPath;
    }

    public String getExportKindUrl(String kind) {
        return new UrlBuilder()
                .setProtocol(Window.Location.getProtocol())
                .setHost(Window.Location.getHost())
                .setPath(restPath + "/" + EndPoints.EXPORT_JSON)
                .setParameter(UrlParameters.KIND, kind)
                .buildString();
    }

    public String getExportCsv(String kind) {
        return new UrlBuilder()
                .setProtocol(Window.Location.getProtocol())
                .setHost(Window.Location.getHost())
                .setPath(restPath + "/" + EndPoints.EXPORT_CSV)
                .setParameter(UrlParameters.KIND, kind)
                .buildString();
    }
}
