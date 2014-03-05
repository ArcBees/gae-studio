package com.arcbees.gaestudio.server.email;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.inject.Inject;

import com.arcbees.gaestudio.server.GaeStudioConstants;
import com.arcbees.gaestudio.server.exception.Utf8Exception;
import com.arcbees.gaestudio.server.velocity.VelocityWrapper;
import com.arcbees.gaestudio.server.velocity.VelocityWrapperFactory;

public class ResetPasswordEmailBuilder {
    private final static String templateLocation =
            "com/arcbees/gaestudio/server/velocitytemplates/messages/resetpasswordtemplate.vm";
    private final VelocityWrapper velocityWrapper;

    @Inject
    ResetPasswordEmailBuilder(VelocityWrapperFactory velocityWrapperFactory) {
        this.velocityWrapper = velocityWrapperFactory.create(templateLocation);
    }

    public String generateBody(String email,
                               String token) {
        velocityWrapper.put("baseurl", GaeStudioConstants.ARCBEES_OAUTH_SERVICE);
        velocityWrapper.put("token", token);
        velocityWrapper.put("email", encode(email));

        return velocityWrapper.generate();
    }

    private String encode(String email) {
        try {
            return URLEncoder.encode(email, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new Utf8Exception();
        }
    }
}
