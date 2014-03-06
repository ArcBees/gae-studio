/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.email;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.arcbees.gaestudio.server.GaeStudioConstants;
import com.arcbees.gaestudio.server.velocity.VelocityWrapper;
import com.arcbees.gaestudio.server.velocity.VelocityWrapperFactory;
import com.google.inject.Inject;

public class EmailMessageGenerator {
    private final VelocityWrapper velocityWrapper;

    @Inject
    EmailMessageGenerator(VelocityWrapperFactory velocityWrapperFactory) {
        this.velocityWrapper = velocityWrapperFactory.create(GaeStudioConstants.TEMPLATE_MAIL_WRAPPER);
    }

    public String generateBody(String title, String message) {
        velocityWrapper.put("msgDate", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        velocityWrapper.put("title", title);
        velocityWrapper.put("textMessage", message);

        return velocityWrapper.generate();
    }
}
