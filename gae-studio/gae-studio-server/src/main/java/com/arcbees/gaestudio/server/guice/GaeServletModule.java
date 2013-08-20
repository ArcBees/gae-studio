/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.guice;

import javax.servlet.ServletContext;

import com.arcbees.gaestudio.server.analytic.AnalyticModule;
import com.arcbees.gaestudio.server.exception.ExceptionModule;
import com.arcbees.gaestudio.server.recorder.GaeStudioRecorderModule;
import com.arcbees.gaestudio.server.rest.RestModule;
import com.arcbees.gaestudio.server.service.ServiceModule;
import com.arcbees.gaestudio.server.velocity.VelocityModule;
import com.arcbees.gaestudio.shared.BaseRestPath;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.google.common.base.Strings;
import com.google.inject.servlet.ServletModule;

import static org.jboss.resteasy.plugins.server.servlet.ResteasyContextParameters.RESTEASY_SERVLET_MAPPING_PREFIX;

public class GaeServletModule extends ServletModule {
    private final String restPath;

    GaeServletModule(ServletContext servletContext) {
        String restEasyPrefix = servletContext.getInitParameter(RESTEASY_SERVLET_MAPPING_PREFIX);

        if (Strings.isNullOrEmpty(restEasyPrefix) || "/*".equals(restEasyPrefix)) {
            restPath = "";
        } else {
            restPath = (restEasyPrefix + "/").replace("//", "/");
        }
    }

    GaeServletModule(String restPath) {
        this.restPath = restPath.replace("//", "/");
    }

    @Override
    protected void configureServlets() {
        install(new GaeStudioRecorderModule());
        install(new VelocityModule());
        install(new RestModule());
        install(new AnalyticModule());
        install(new ServiceModule());
        install(new ExceptionModule());

        bindConstant().annotatedWith(BaseRestPath.class).to(restPath);

        String baseRestPath = restPath == null ? "/" : "/" + restPath + "/";
        String fullRestPath = (baseRestPath + EndPoints.REST_PATH).replace("//", "/");

        filter(fullRestPath + "*").through(GuiceRestEasyFilterDispatcher.class);
    }
}
