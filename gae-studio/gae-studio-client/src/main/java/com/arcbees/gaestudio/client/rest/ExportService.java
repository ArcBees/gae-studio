/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
