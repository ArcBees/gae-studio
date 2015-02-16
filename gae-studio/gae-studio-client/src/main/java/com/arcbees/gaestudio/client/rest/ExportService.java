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
import com.google.common.base.Strings;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.gwtplatform.dispatch.rest.client.RestApplicationPath;

public class ExportService {
    private final String restPath;

    @Inject
    ExportService(
            @RestApplicationPath String restPath) {
        this.restPath = restPath;
    }

    public String getExportJson(String kind, String namespace, String encodedKeys) {
        return getExportPath(EndPoints.EXPORT_JSON, kind, namespace, encodedKeys);
    }

    public String getExportCsv(String kind, String namespace, String encodedKeys) {
        return getExportPath(EndPoints.EXPORT_CSV, kind, namespace, encodedKeys);
    }

    private String getExportPath(String endpoint, String kind, String namespace, String encodedKeys) {
        UrlBuilder urlBuilder = new UrlBuilder()
                .setProtocol(Window.Location.getProtocol())
                .setHost(Window.Location.getHost())
                .setPath(restPath + "/" + endpoint);

        if (Strings.isNullOrEmpty(encodedKeys)) {
            urlBuilder.setParameter(UrlParameters.KIND, kind);
            if (namespace != null) {
                urlBuilder.setParameter(UrlParameters.NAMESPACE, namespace);
            }
        } else {
            urlBuilder.setParameter(UrlParameters.KEY, encodedKeys);
        }

        return urlBuilder.buildString();
    }
}
