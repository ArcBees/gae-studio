/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.email;

import com.arcbees.gaestudio.server.velocity.VelocityWrapper;
import com.arcbees.gaestudio.server.velocity.VelocityWrapperFactory;
import com.google.inject.Inject;

public class ConfirmRegistrationEmailBuilder {
    private final static String templateLocation =
            "com/arcbees/gaestudio/server/velocitytemplates/messages/confirmregistration.vm";
    private final VelocityWrapper velocityWrapper;

    @Inject
    ConfirmRegistrationEmailBuilder(VelocityWrapperFactory velocityWrapperFactory) {
        this.velocityWrapper = velocityWrapperFactory.create(templateLocation);
    }

    public String generateBody(String tokenId, String redirectionUri) {
        velocityWrapper.put("redirectionUri", redirectionUri);
        velocityWrapper.put("tokenId", tokenId);

        return velocityWrapper.generate();
    }
}
