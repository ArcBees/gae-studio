package com.arcbees.gaestudio.server.rest.auth;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.arcbees.oauth.client.OAuthClient;
import com.arcbees.oauth.client.UserClient;
import com.arcbees.oauth.client.domain.Token;
import com.arcbees.oauth.client.domain.User;

@Path(EndPoints.AUTH)
public class AuthResource {
    public static final String API_TOKEN = "ljhs98234h24o8dsyfjehrljqh01923874j2hj";
    private final OAuthClient oAuthClient;
    private final UserClient userClient;

    @Inject
    AuthResource(OAuthClient oAuthClient,
                 UserClient userClient) {
        this.oAuthClient = oAuthClient;
        this.userClient = userClient;
    }

    @Path(EndPoints.REGISTER)
    public Response register(@FormParam(UrlParameters.EMAIL) String email,
                             @FormParam(UrlParameters.PASSWORD) String password,
                             @FormParam(UrlParameters.FIRST_NAME) String firstName,
                             @FormParam(UrlParameters.LAST_NAME) String lastName) {

        Token bearerToken = oAuthClient.getBearerToken(API_TOKEN);

        User user = userClient.register(bearerToken, email, password, firstName, lastName);

        return Response.ok(user).build();
    }
}
