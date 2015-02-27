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

package com.arcbees.gaestudio.server.guice;

import javax.inject.Singleton;
import javax.servlet.ServletContext;

import com.arcbees.gaestudio.server.ConnectFilter;
import com.arcbees.gaestudio.server.analytic.AnalyticModule;
import com.arcbees.gaestudio.server.api.ApiModule;
import com.arcbees.gaestudio.server.appengine.AppEngineModule;
import com.arcbees.gaestudio.server.channel.ChannelModule;
import com.arcbees.gaestudio.server.dto.mapper.MapperModule;
import com.arcbees.gaestudio.server.exception.ExceptionModule;
import com.arcbees.gaestudio.server.guice.devserver.DevServerModule;
import com.arcbees.gaestudio.server.recorder.GaeStudioRecorderModule;
import com.arcbees.gaestudio.server.service.ServiceModule;
import com.arcbees.gaestudio.server.util.UtilModule;
import com.arcbees.gaestudio.server.velocity.VelocityModule;
import com.arcbees.gaestudio.shared.BaseRestPath;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.google.appengine.api.datastore.GsonModule;
import com.google.appengine.api.utils.SystemProperty;
import com.google.common.base.Strings;
import com.google.inject.servlet.ServletModule;

import static org.jboss.resteasy.plugins.server.servlet.ResteasyContextParameters.RESTEASY_SERVLET_MAPPING_PREFIX;

public class CommonModule extends ServletModule {
    private final String restPath;

    public CommonModule(ServletContext servletContext) {
        String restEasyPrefix = servletContext.getInitParameter(RESTEASY_SERVLET_MAPPING_PREFIX);

        if (Strings.isNullOrEmpty(restEasyPrefix) || "/*".equals(restEasyPrefix)) {
            restPath = "";
        } else {
            restPath = (restEasyPrefix + "/").replace("//", "/");
        }
    }

    CommonModule(String restPath) {
        this.restPath = restPath.replace("//", "/");
    }

    @Override
    protected void configureServlets() {
        // ApiModule needs to be loaded before AnalyticsModule, since it binds interceptors in the rest package
        install(new ApiModule());
        install(new AnalyticModule());
        install(new ExceptionModule());
        install(new GaeStudioRecorderModule());
        install(new GsonModule());
        install(new MapperModule());
        install(new ServiceModule());
        install(new VelocityModule());
        install(new AppEngineModule());
        install(new UtilModule());
        install(new ChannelModule());

        bindConstant().annotatedWith(BaseRestPath.class).to(restPath);

        String fullRestPath = parseFullRestPath();
        filter(fullRestPath + "*").through(GuiceRestEasyFilterDispatcher.class);

        bind(ConnectFilter.class).in(Singleton.class);
        filter("/_ah/channel/connected/", "/_ah/channel/disconnected/").through(ConnectFilter.class);

        if (SystemProperty.environment.value() != SystemProperty.Environment.Value.Production) {
            install(new DevServerModule());
        }
    }

    private String parseFullRestPath() {
        String baseRestPath = restPath == null ? "/" : "/" + restPath + "/";
        return (baseRestPath + EndPoints.REST_PATH).replace("//", "/");
    }
}
