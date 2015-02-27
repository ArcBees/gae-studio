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

package com.arcbees.gaestudio.server.velocity;

import java.io.StringWriter;

import javax.inject.Inject;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.arcbees.gaestudio.server.guice.GaeStudioResource;
import com.google.inject.assistedinject.Assisted;

public class VelocityWrapper {
    private final Template template;
    private final VelocityContext velocityContext = new VelocityContext();

    @Inject
    VelocityWrapper(
            @GaeStudioResource VelocityEngine velocityEngine,
            @Assisted String templateLocation) {
        this.template = velocityEngine.getTemplate(templateLocation);
    }

    public VelocityWrapper put(String key, Object value) {
        velocityContext.put(key, value);

        return this;
    }

    public String generate() {
        StringWriter writer = new StringWriter();

        template.merge(velocityContext, writer);

        return writer.toString();
    }
}
