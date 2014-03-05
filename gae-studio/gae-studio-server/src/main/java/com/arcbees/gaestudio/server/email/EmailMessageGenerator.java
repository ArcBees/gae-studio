package com.arcbees.gaestudio.server.email;

import com.arcbees.gaestudio.server.velocity.VelocityWrapper;
import com.arcbees.gaestudio.server.velocity.VelocityWrapperFactory;
import com.google.inject.Inject;

public class EmailMessageGenerator {
    private final static String templateLocation =
            "com/arcbees/gaestudio/server/velocitytemplates/messages/confirmregistration.vm";
    private final VelocityWrapper velocityWrapper;

    @Inject
    EmailMessageGenerator(VelocityWrapperFactory velocityWrapperFactory) {
        this.velocityWrapper = velocityWrapperFactory.create(templateLocation);
    }

    public String generateBody(String tokenId, String redirectionUri) {
        velocityWrapper.put("redirectionUri", redirectionUri);
        velocityWrapper.put("tokenId", tokenId);

        return velocityWrapper.generate();
    }
}
