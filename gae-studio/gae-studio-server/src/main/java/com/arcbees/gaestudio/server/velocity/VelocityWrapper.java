/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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
    VelocityWrapper(@GaeStudioResource VelocityEngine velocityEngine,
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
